package Game;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameCombat {
    private static final Random rand = new Random();
    private Scanner sc;

    public GameCombat() {
        this.sc = new Scanner(System.in);
    }

    public void startCombat(Character player, Enemy enemy) {
        while (player.getCurrentHealth() > 0 && enemy.getCurrentHealth() > 0) {
            // Tura player-ului
            playerTurn(player, enemy);

            if (enemy.getCurrentHealth() <= 0) {
                System.out.println(player.getCharacterName() + " A CASTIGAT LUPTA CEA MARE!");
                handleVictory(player);
                break;
            }

            // Tura inamicului
            if (enemy.getCurrentHealth() > 0) {
                enemyTurn(player, enemy);

                if (player.getCurrentHealth() <= 0) {
                    System.out.println("AI FOST INVINS DE INAMIC! Mult noroc data viitoare " + player.getCharacterName());
                    Game gameInstance = player.getGameInstance();
                    if (gameInstance != null) {
                        gameInstance.gameOver();
                    }
                    break;
                }
            }
        }

        // Stare finala jucator
        displayPlayerStats(player);
    }

    private void playerTurn(Character player, Enemy enemy) {
        System.out.println("Tura jucatorului: ");
        System.out.println("1. Ataca");
        System.out.println("2. Foloseste abilitate");

        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                int damage = player.calculateBaseDamage();
                System.out.println(player.getCharacterName() + " a folosit un Basic Attack!");
                enemy.receiveDamage(damage);
                break;
            case "2":
                useAbility(player, enemy);
                break;
            default:
                System.out.println("Optiune invalida!");
                playerTurn(player, enemy);  // Alege din nou
                break;
        }
    }

    // Folosirea abilitatii de jucator
    private void useAbility(Character player, Enemy enemy) {
        if (player.getCurrentMana() < 25) {
            System.out.println("Nu ai suficienta mana pentru a folosi o abilitate! Calculeaza-ti miscarile mai bine!");
            playerTurn(player, enemy);
            // int damage = player.calculateBaseDamage();
            // enemy.receiveDamage(damage);
            return;
        }

        Spell chosenSpell = null;
        while (chosenSpell == null) {
            System.out.println("Alege o abilitate: ");
            showAbilities(player);

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> chosenSpell = player.getAbilities().get(0);
                case "2" -> chosenSpell = player.getAbilities().get(1);
                case "3" -> chosenSpell = player.getAbilities().get(2);
                default -> System.out.println("Optiune invalida! Incearca din nou.");
            }
        }

        if (player.getCurrentMana() >= chosenSpell.getManaCost()) {
            enemy.receiveDamage(chosenSpell.getDamage());
            player.reduceMana(chosenSpell.getManaCost());
            player.getAbilities().remove(chosenSpell);
            System.out.println(player.getCharacterName() + " foloseste " + chosenSpell.getClass().getSimpleName() +
                    " pentru " + chosenSpell.getDamage() + " daune. Mana ramasa: " + player.getCurrentMana() + " Mana");
        } else {
            System.out.println("Nu ai suficienta mana pentru a folosi aceasta abilitate! Calculeaza-ti miscarile mai bine!");
            playerTurn(player, enemy);
        }
    }

    private void showAbilities(Character player) {
//        System.out.println("1. Ice - Daune: 30, Mana necesara: 25");
//        System.out.println("2. Fire - Daune: 40, Mana necesara: 40");
//        System.out.println("3. Earth - Daune: 35, Mana necesara: 25");
//        System.out.println("Mana ramasa: " + player.getCurrentMana());

        System.out.println("Abilitati disponibile:");

        List<Spell> abilities = player.getAbilities();
        if (abilities.isEmpty()) {
            System.out.println("Nu mai ai abilitati disponibile!");
            return;
        }

        for (int i = 0; i < abilities.size(); i++) {
            Spell spell = abilities.get(i);
            System.out.println((i+1) + ". " + spell.getClass().getSimpleName() + " - Daune: " + spell.getDamage() + ", Mana necesara: " + spell.getManaCost());
        }
        System.out.println("Mana ramasa: " + player.getCurrentMana());
    }

    // Aleg o abilitate aleatorie (pentru inamic)
    private Spell chooseSpell(Character player) {
        Random rand = new Random();
        return player.getAbilities().get(rand.nextInt(player.getAbilities().size()));
    }

    // Tura inamicului
    private void enemyTurn(Character player, Enemy enemy) {
        System.out.println("Tura inamicului: ");
        if (enemy.getCurrentMana() >= 20) {
            enemyUseAbility(enemy, player);
        } else {
            enemyAttack(enemy, player);
        }
    }

    // Folosirea unei abilitati de catre inamic
    private void enemyUseAbility(Enemy enemy, Character player) {
        if(!enemy.getAbilities().isEmpty()) {
            Random rand  = new Random();
            int chooseAbility = rand.nextInt(enemy.getAbilities().size());
            Spell chosenAbility = enemy.getAbilities().get(chooseAbility);
            player.receiveDamage(chosenAbility.getDamage());
            enemy.reduceMana(chosenAbility.getManaCost());
            System.out.println("Abilitatea inamicului a costat " + chosenAbility.getManaCost() + " mana! Mana ramasa " + enemy.getCurrentMana());
            System.out.println("Inamicul foloseste abilitatea " + chosenAbility.getClass().getSimpleName());
        }
        else {
            System.out.println("Inamicul nu are abilitati");
        }
    }

    // Atacul normal al inamicului
    private void enemyAttack(Enemy enemy, Character player) {
        int damage = enemy.getDamage();
        System.out.println("Inamicul ataca cu " + damage + " daune.");
        player.receiveDamage(damage);
    }

    private void handleVictory(Character player) {
        int xpGained = rand.nextInt(10) + 5;
        System.out.println(player.getCharacterName() + " A CASTIGAT " + xpGained + " PUNCTE DE EXPERIENTA!");
        player.addExperience(xpGained);
        player.regenerateHealth(20);
        player.regenerateMana(40);
    }

    // Afisare stare actualizata a jucatorului dupa lupta
    private void displayPlayerStats(Character player) {
        // Stiu ca viata trebuie sa se dubleze si mana se reseteaza dar o las ca sa fie mai distractiv
        // Daca le resetez atunci o sa reusesc sa bat orice inamic mereu si nu o sa mai fie competitiv
        System.out.println("Starea finala a caracterului " + player.getCharacterName() + ":");
        System.out.println("Viata ramasa: " + player.getCurrentHealth());
        System.out.println("Mana ramasa: " + player.getCurrentMana());
        System.out.println("Experienta actuala: " + player.getCharacterXP() + "! Continua pentru level up!" + ((player.getCharacterLevel() * 10) - player.characterXP) + " XP ramas pana la LEVEL UP");
    }
}
