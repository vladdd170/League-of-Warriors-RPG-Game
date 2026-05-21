package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class Account {
    private Information information;
    private ArrayList<Character> characters;
    private int gamesPlayed;

    public Account(ArrayList<Character> characters, int gamesPlayed, Information information) {
        this.characters = characters;
        this.gamesPlayed = gamesPlayed;
        this.information = information;
    }

    public boolean authenticate(String email, String password) {
        // Compar email-ul si parola
        Credentials credentials = this.information.getCredentials();
        return credentials.getEmail().equals(email) && credentials.getPassword().equals(password);
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void increaseGamesPlayed() {
        this.gamesPlayed++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Informatii despre cont:\n");
        sb.append("Nume: ").append(information.getName()).append("\n");
        sb.append("Tara: ").append(information.getCountry()).append("\n");
        sb.append("Jocuri favorite: ").append(information.getFavoriteGames()).append("\n");
        sb.append("Caractere disponibile:\n");
        int index = 1;

        if (characters.isEmpty()) {
            sb.append("  Nu exista caractere disponibile.\n");
        } else {
            for (Character character : characters) {
                sb.append(index + " ) ").append(character.getCharacterName())
                        .append(" (Nivel: ").append(character.getCharacterLevel()).append(")\n");
                index++;
            }
        }

        sb.append("Harti jucate: ").append(gamesPlayed).append("\n");
        return sb.toString();
    }

    public Character afisare(int i) {
        int indexCurent = 1;
        for (Character character : characters) {
            if (indexCurent == i) {
                System.out.println("Ai ales caracterul " + i + ") " + character.getCharacterName());
                System.out.println("Hai la luptă!");
                return character;
            }
            indexCurent++;
        }
        System.out.println("Caracter invalid!");
        return null;
    }




    static class Information {
        private Credentials credentials;
        private SortedSet<String> favoriteGames;
        private String name;
        private String country;

        public Information(Credentials credentials, String name, String country, SortedSet<String> favoriteGames) {
            this.credentials = credentials;
            this.name = name;
            this.country = country;
            this.favoriteGames = favoriteGames;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public void setCredentials(Credentials credentials) {
            this.credentials = credentials;
        }

        public SortedSet<String> getFavoriteGames() {
            return favoriteGames;
        }

        public void setFavoriteGames(SortedSet<String> favoriteGames) {
            this.favoriteGames = favoriteGames;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}