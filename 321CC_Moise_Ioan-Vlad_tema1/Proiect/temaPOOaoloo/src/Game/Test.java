//package Game;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Random;
//import java.util.Scanner;
//import java.util.TreeSet;
//import java.util.ArrayList;
//
//public class Test {
//    private GameState gameState = GameState.EXPLORATION;
//
//    public void Test() {
//        Scanner scanner = new Scanner(System.in);
//
//        // Creez harta fixa de 5x5
//        Grid grid = new Grid(5, 5);
//        ArrayList<ArrayList<Cell>> map = new ArrayList<>();
//
//        // Inițializez tabla
//        for (int i = 0; i < 5; i++) {
//            ArrayList<Cell> row = new ArrayList<>();
//            for (int j = 0; j < 5; j++) {
//                row.add(new Cell(i, j, CellEntityType.VOID));
//            }
//            map.add(row);
//        }
//
//        // Plasarea elementelor conform hartii
//        map.get(0).set(0, new Cell(0, 0, CellEntityType.PLAYER)); // P
//        map.get(0).set(3, new Cell(0, 3, CellEntityType.SANCTUARY)); // S
//        map.get(1).set(3, new Cell(1, 3, CellEntityType.SANCTUARY)); // S
//        map.get(2).set(0, new Cell(2, 0, CellEntityType.SANCTUARY)); // S
//        map.get(3).set(4, new Cell(3, 4, CellEntityType.ENEMY)); // E
//        map.get(4).set(3, new Cell(4, 3, CellEntityType.SANCTUARY)); // S
//        map.get(4).set(4, new Cell(4, 4, CellEntityType.PORTAL)); // F
//
//        // Setez playerul și celula inițială
//        grid.setPlayer(new Warrior("TestPlayer", 0, 1));
//        grid.setCurrentCell(map.get(0).get(0));
//        grid.clear();
//        grid.addAll(map);
//
//        // Jocul rulează până când ajungem la portal
//        boolean gameRunning = true;
//        System.out.println("Introduceți mutări: N (Nord), S (Sud), E (Est), W (Vest)");
//        print(grid);
//        Scanner scan= new Scanner(System.in);
//
//        while (true) {
//            if (gameState == GameState.EXPLORATION) {
//                String input = scan.nextLine().toUpperCase();
//                try {
//                    switch (input) {
//                        case "W":
//                            this.grid.goNorth();
//                            print(this.grid);
//                            break;
//                        case "S":
//                            this.grid.goSouth();
//                            print(this.grid);
//                            break;
//                        case "A":
//                            this.grid.goWest();
//                            print(this.grid);
//                            break;
//                        case "D":
//                            this.grid.goEast();
//                            print(this.grid);
//                            break;
//                        case "QUIT":
//                            System.out.println("Ai parasit jocul");
//                            System.out.flush();
//                            System.exit(0);
//                            return;
//                        default:
//                            System.out.println("Tasta invalida!");
//                            continue;
//                    }
//                    if (this.grid.getCurrentCell().hasEnemy()) {
//                        System.out.println("Ai intalnit un inamic! Mult noroc!");
//                        Enemy enemy = this.grid.getCurrentCell().getEnemy();
//                        gameState = GameState.COMBAT;
//                        GameCombat combat = new GameCombat();
//                        if (grid.getPlayer().getAbilities().size() < 3) {
//                            grid.getPlayer().refreshAbilities();
//                        }
//                        combat.startCombat(grid.getPlayer(), enemy);
//                        print(this.grid);
//                        gameState = GameState.EXPLORATION;
//                    }
//                } catch (Exception e) {
//                    //e.printStackTrace();
//                    System.out.println("Nu poti muta aici!");
//                }
//            }
//        }
//
//        scanner.close();
//    }
//
//    private static void print(Grid grid) {
//        for (int i = 0; i < grid.size(); i++) {
//            for (int j = 0; j < grid.get(i).size(); j++) {
//                System.out.print(grid.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }
//    }
//    }
//
//    public static void main(String[] args) {
//
//}
