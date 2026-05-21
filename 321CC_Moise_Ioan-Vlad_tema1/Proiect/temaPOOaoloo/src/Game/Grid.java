package Game;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private static final int MAX_SIZE = 10; // maxul tablei
    private final int length;
    private final int width;
    private Character player;
    private Cell currentCell;

    public Grid(int length, int width) {
        if ( length > MAX_SIZE || width > MAX_SIZE) {
            throw new IllegalArgumentException("Dimensiunea maxima atinsa este 10x10");
        }
        this.length = length;
        this.width = width;
    }

    public static Grid generateMap(int length, int width) {
        Grid grid = new Grid(5, 5); // Grid grid = new Grid(length, width);
        Random rand = new Random();

        // Formez tabla goala
        for(int i = 0 ; i < 5; i++) { // length defapt NU 5
            ArrayList<Cell> row = new ArrayList<>();
            for(int j = 0; j < 5; j++) { // width defapt NU 5 pt test este
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }


        Cell playerCell = new Cell(0, 0, CellEntityType.PLAYER);
        grid.get(0).set(0, playerCell);
        grid.setCurrentCell(playerCell);

        // 2 sanctuare - INITIALA RANDOM DAR TEST CUM E N POZA
//        for(int i = 0 ; i < 2; i++) {
//            placeRandomEntity(grid, CellEntityType.SANCTUARY, rand);
//        }
        // DE COMENTAT DACA NU VREI HARDCODARE
        Cell sanctuarCell1 = new Cell(0, 3, CellEntityType.SANCTUARY);
        grid.get(0).set(3, sanctuarCell1);
        //grid.setCurrentCell(sanctuarCell1);
        Cell sanctuarCell2 = new Cell(1, 3, CellEntityType.SANCTUARY);
        grid.get(1).set(3, sanctuarCell2);
        //grid.setCurrentCell(sanctuarCell2);
        Cell sanctuarCell3 = new Cell(2, 0, CellEntityType.SANCTUARY);
        grid.get(2).set(0, sanctuarCell3);

        // 4 inamici - LA FEL SE DECOMENTEAZA PT COD NORMAL
//        for(int i = 0 ; i < 4; i++) {
//            Cell enemyCell = placeRandomEntity(grid, CellEntityType.ENEMY, rand);
//            enemyCell.setEnemy(new Enemy());
//        }
        // DE COMENTAT DACA NU VREI HARDCODARE
        Cell enemyCell = new Cell(3, 4, CellEntityType.ENEMY);
        grid.get(3).set(4, enemyCell);
        enemyCell.setEnemy(new Enemy());
        // pana aici

        Cell portalCell = new Cell(4, 4, CellEntityType.PORTAL); // Cell portalCell = new Cell(length-1, width-1, CellEntityType.PORTAL);
        grid.get(4).set(4, portalCell); //grid.get(length-1).set(width-1, portalCell);

        grid.get(0).get(0).setVisited(true);
        grid.get(0).get(0).setOriginalType(CellEntityType.VOID);
        return grid;
    }

    public static Cell placeRandomEntity(Grid grid, CellEntityType entityType, Random random) {
        int x, y;
        do {
            x = random.nextInt(grid.length);
            y = random.nextInt(grid.width);
        } while(grid.get(x).get(y).getCellType() != CellEntityType.VOID);

        Cell cell = new Cell(x, y, entityType);
        grid.get(x).set(y, cell);
        return cell;
    }

    public void moveToCell(int x, int y) {
        if (currentCell != null) {
            // Marchez celula anterioara ca vizitata si revin la tipul sau original
            currentCell.setCellType(currentCell.getOriginalType());
            currentCell.setVisited(true);
        }

        currentCell = get(x).get(y);
        currentCell.setCellType(CellEntityType.PLAYER);
        currentCell.setVisited(true);

        if (currentCell.getOriginalType() == CellEntityType.SANCTUARY) {
            if(!currentCell.isSanctuaryVisited()) {
                Random rand = new Random();
                int healtBoost = rand.nextInt(40) + 20;
                int manaBoost = rand.nextInt(40) + 20;

                System.out.println("Ai ajuns intr-un SANCTUAR! Primesti recompensa +" + healtBoost + " Viata si +" + manaBoost + " Mana");

                if (player != null) {
                    player.regenerateHealth(healtBoost);
                    player.regenerateMana(manaBoost);
                    System.out.println("Nivelul de Viata curenta al caracterului " + player.getCharacterName() + " este " + player.currentHealth);
                    System.out.println("Nivelul de Mana curenta al caracterului " + player.getCharacterName() + " este " + player.currentMana + "\n");
                } else {
                    System.out.println("Nu exista un jucator activ pentru a primi bonusul");
                }
                currentCell.setSanctuaryVisited(true);
            }
            else {
                System.out.println("Sanctuarul a fost deja vizitat!");
            }
        }

        if (currentCell.getOriginalType() == CellEntityType.PORTAL) {
            System.out.println("Felicitari! Ai terminat harta!");
            player.increaseGamesPlayed();
            System.out.println("Numar de harti completate astazi:" + player.getGamesPlayed() + "\n");

            int experienceGained = player.getCharacterLevel() * 5;
            System.out.println("Ai castigat " + experienceGained + " puncte de experienta.");
            player.addExperience(experienceGained);

            System.out.println("Nivelul caracterului tau este acum: " + player.getCharacterLevel());
            System.out.println("Statistici Caracter: Forta: " + player.getStrength() + ", Carisma: " + player.getCharisma() + ", Dexteritate: " + player.getDexterity());
            System.out.println("Experienta caracterului tau este acum: " + getPlayer().getCharacterXP() + "\n");

            player.resetHealthAndMana();
            System.out.println("Jucătorul a fost mutat pe o hartă noua.");
            if (player.getGameInstance() != null) {
                player.getGameInstance().notifyGameForNewMap();
            }
        }
    }

    public void goNorth() throws Exception {
        int x= currentCell.getX();
        int y= currentCell.getY();
        if ( x - 1 < 0 ) {
            throw new Exception("Nu te poti deplasa la nord");
        }
        moveToCell(x - 1, y);
    }

    public void goSouth() throws Exception {
        int x= currentCell.getX();
        int y= currentCell.getY();
        if ( x + 1 >= length ) {
            throw new Exception("Nu te poti deplasa la sud");
        }
        moveToCell(x + 1, y);
    }

    public void goEast() throws Exception {
        int x= currentCell.getX();
        int y= currentCell.getY();
        if ( y + 1 >= width ) {
            throw new Exception("Nu te poti deplasa la est");
        }
        moveToCell(x, y + 1);
    }

    public void goWest() throws Exception {
        int x= currentCell.getX();
        int y= currentCell.getY();
        if ( y - 1 < 0 ) {
            throw new Exception("Nu te poti deplasa la est");
        }
        moveToCell(x, y - 1);
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Character getPlayer() {
        return player;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }
}
