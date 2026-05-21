package Game;

public class Cell {
    private final int x;
    private final int y;
    private CellEntityType cellType;
    private CellEntityType originalType;
    private boolean visited; // celula vizitata
    private boolean isSanctuaryVisited;
    private Enemy enemy;

    public Cell(int x, int y, CellEntityType cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
        this.visited = false;
        this.originalType = cellType;
        this.isSanctuaryVisited = false;
        this.enemy = null;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public boolean hasEnemy() {
        return enemy != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellEntityType getCellType() {
        return cellType;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isSanctuaryVisited() {
        return isSanctuaryVisited;
    }

    public void setSanctuaryVisited(boolean sanctuaryVisited) {
        isSanctuaryVisited = sanctuaryVisited;
    }

    public CellEntityType getOriginalType() {
        return originalType;
    }

    public void setOriginalType(CellEntityType originalType) {
        this.originalType = originalType;
    }

    public void setCellType(CellEntityType cellType) {
        this.cellType = cellType;
    }

    @Override
    public String toString() {
        if (cellType == CellEntityType.PLAYER) {
            return "P";
        }
        else if(!visited && cellType == CellEntityType.PORTAL) {
            return "F";
        }
        else if (!visited) {
            return "N";
        }

        switch (cellType) {
            case ENEMY:
                return "E";
            case PORTAL:
                return "F";
            case SANCTUARY:
                return "S";
            default:
                return "V";
        }
    }
}
