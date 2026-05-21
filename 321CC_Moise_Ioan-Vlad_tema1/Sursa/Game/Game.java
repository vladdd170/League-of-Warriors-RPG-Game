package Game;


import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.ArrayList;


// Versiunea care ruleaza este cea harcodata cu harta 5x5, 3 sanctuar si un
// inamic. Atunci cand ajung pe portal mi se genereaza iarasi harta 5x5,
// 3 sanctuar si un inamic pentru ca este HARDCODATA
// Am adaugat in dreptul la fiecare linie cu ce trebuie inlocuit ca jocul sa
//ruleze intr-un mod natural si fluent, iar toate hartiile se vor genera random
public class Game {
    private ArrayList<Account> accounts = new ArrayList<>();
    private Account currAccount;
    private Character currCharacter;
    private Grid grid;
    private ArrayList<Credentials> credentialsList = new ArrayList<>();
    private Credentials currentCredentials;
    private GameState gameState = GameState.EXPLORATION;
    private int contorRestart = 0;

    public void retineHarta(){
        currCharacter.setGameInstance(this);
    }

    public void restartGame() {
        gameState = GameState.EXPLORATION;
        currCharacter.resetHealthAndMana();
        loginSystem();
    }

    public void gameOver() {
        System.out.println("Game Over! Vrei sa incerci din nou? (da/nu)");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toLowerCase();

        if(input.equals("da")){
            restartGame();
        }
        else if(input.equals("nu")){
            System.out.println("Jocul s-a terminat! Pa!");
            System.exit(0);
        }
        else {
            System.out.println("O sa o iau ca pe un nu! Pa!");
            System.exit(0);
        }
    }

    public Game() {
        try{
            // Parsez fisierul JSON
            String content = new String(Files.readAllBytes(Paths.get("accounts.json")));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray("accounts");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject accountJson = jsonArray.getJSONObject(i);

                JSONObject credentialsJson = accountJson.getJSONObject("credentials");
                String password = credentialsJson.getString("password");
                String mail = credentialsJson.getString("email");
                String name = accountJson.getString("name");
                String countryPlayer = accountJson.getString("country");
                Credentials credentials = new Credentials(mail, password);
                credentialsList.add(credentials);

                JSONArray charactersArray = accountJson.getJSONArray("characters");
                ArrayList<Character> gameCharacters = new ArrayList<>();
                int mapsCompleted = accountJson.getInt("maps_completed");
                for (int j = 0; j < charactersArray.length(); j++) {
                    JSONObject characterJson = charactersArray.getJSONObject(j);
                    String characterProfessionType = characterJson.getString("profession");
                    String characterRealName = characterJson.getString("name");
                    int xp = characterJson.getInt("experience");
                    int lvl = characterJson.getInt("level");

                    switch (characterProfessionType) {
                        case "Warrior":
                            gameCharacters.add(new Warrior(characterRealName, xp, lvl));
                            break;
                        case "Mage":
                            gameCharacters.add(new Mage(characterRealName, xp, lvl));
                            break;
                        case "Rogue":
                            gameCharacters.add(new Rogue(characterRealName, xp, lvl));
                            break;
                        default:
                            System.out.println("Nu exista tipul asta de caracter");
                    }
                }

                TreeSet<String> favouriteRpgGames = new TreeSet<>();
                JSONArray favoriteGamesArray = accountJson.getJSONArray("favorite_games");
                for (int j = 0; j < favoriteGamesArray.length(); j++) {
                    favouriteRpgGames.add(favoriteGamesArray.getString(j));
                }

                // Adaug informatiile parsate din JSON
                Account account = new Account(gameCharacters, mapsCompleted, new Account.Information(credentials, name, countryPlayer, favouriteRpgGames));
                accounts.add(account);
            }
        }catch(Exception e){
            System.out.println("Eroare la incare din JSON");
        }

    }
    public void loginSystem(){
        Scanner sc = new Scanner(System.in);
        String username, password;
        if(contorRestart == 0) {
            System.out.print("Email: ");
            username = sc.nextLine();
            System.out.print("Password: ");
            password = sc.nextLine();
            contorRestart = 1;
        }
        else {
            username = currentCredentials.getEmail();
            password = currentCredentials.getPassword();
        }
        currentCredentials = authenticateAccount(username, password);
        if(currentCredentials != null){
            System.out.println("Autentificare reusita");
            Account account = getAccount();
            if(account != null){
                System.out.println(account);
            }
            System.out.println("Alege caracterul");
            String inputNr = sc.nextLine();

            try {
                // incerc sa convertesc intr-un nr intreg
                int inputIntreg = Integer.parseInt(inputNr);

                // Verific daca este intre 1 si 3
                if (inputIntreg < 1 || inputIntreg > 3) {
                    System.out.println("Numar invalid! Alege un index intre 1 si 3.");
                    contorRestart = 1;
                    loginSystem(); // Repet loginSystem pentru index invalid
                    return;
                }

                // Dacă este valid selectez caracterul
                currCharacter = account.afisare(inputIntreg);
                System.out.println("Caracterul curent selectat este: " + currCharacter.getCharacterName());

            } catch (NumberFormatException e) {
                // Inputul nu este numeric
                System.out.println("Input invalid! Te rog sa introduci un numar intre 1 si 3.");
                contorRestart = 1;
                loginSystem(); // Repet loginSystem pentru index invalid
                return;
            }


            Random rand = new Random();
            int length = rand.nextInt(7) + 4;
            int width = rand.nextInt(7) + 4;
            // INLOCUIESTE LINIA COMENTATA CA CODUL SA NU MAI FIE HARDCODAT CU HARTA IMPUSA
            Grid grid = Grid.generateMap(length, width); // Grid grid = Grid.generateMap(length, width);
            grid.setPlayer(currCharacter);
            this.grid = grid;
            currCharacter.setGameInstance(this);

            Scanner scan= new Scanner(System.in);
            print(grid);
            while (true) {
                if (gameState == GameState.EXPLORATION) {
                    String input = scan.nextLine().toUpperCase();
                    try {
                        switch (input) {
                            case "W":
                                this.grid.goNorth();
                                print(this.grid);
                                break;
                            case "S":
                                this.grid.goSouth();
                                print(this.grid);
                                break;
                            case "A":
                                this.grid.goWest();
                                print(this.grid);
                                break;
                            case "D":
                                this.grid.goEast();
                                print(this.grid);
                                break;
                            case "QUIT":
                                System.out.println("Ai parasit jocul");
                                System.out.flush();
                                System.exit(0);
                                return;
                            default:
                                System.out.println("Tasta invalida!");
                                print(this.grid);
                                continue;
                        }
                        if (this.grid.getCurrentCell().hasEnemy()) {
                            System.out.println("Ai intalnit un inamic! Mult noroc!");
                            Enemy enemy = this.grid.getCurrentCell().getEnemy();
                            gameState = GameState.COMBAT;
                            GameCombat combat = new GameCombat();
                            if (currCharacter.getAbilities().size() < 3) {
                                currCharacter.refreshAbilities();
                            }
                            combat.startCombat(currCharacter, enemy);
                            print(this.grid);
                            gameState = GameState.EXPLORATION;
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                        System.out.println("Nu poti muta aici!");
                    }
                }
            }

        } else {
            System.out.println("Autentificare esuata");
        }
        sc.close();
    }

    private Account getAccount(){
        for(Account account : accounts){
            if(account.getInformation().getCredentials().equals(currentCredentials)){
                return account;
            }
        }
        return null;
    }
    private static void print(Grid grid) {
        System.out.println("Deplaseaza-te pe harta cu ajutorul W,A,S,D");
        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(i).size(); j++){
                System.out.print(grid.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    private Credentials authenticateAccount(String username, String password){
        for(Credentials creds : credentialsList){
            if(creds.getEmail().equals(username.trim()) && creds.getPassword().equals(password.trim())){
                return creds;
            }
        }
        return null;
    }

    public void setGrid(Grid newGrid) {
        this.grid = newGrid;
    }

    public void notifyGameForNewMap() {
        // New Grid
        Random rand = new Random();
        int length = rand.nextInt(7) + 4;
        int width = rand.nextInt(7) + 4;
        Grid newGrid = Grid.generateMap(length, width);
        // actualizez harta curenta
        this.grid = newGrid;
        this.grid.setPlayer(this.currCharacter);
        this.grid.setCurrentCell(newGrid.get(0).get(0));
        Account account = getAccount();
        if (account != null) {
            account.increaseGamesPlayed();
            System.out.println("Viata si mana caracterului au fost resetate la valorile maxime (100 hp/mana)");
            System.out.println("Harti completate: " + account.getGamesPlayed() + "\n");
        }

    }

    public void run(){
        loginSystem();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}
