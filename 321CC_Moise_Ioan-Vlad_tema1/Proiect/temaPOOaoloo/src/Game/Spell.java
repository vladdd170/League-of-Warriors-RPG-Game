package Game;

public abstract class Spell {
    private final int damage;
    private final int manaCost;

    public Spell(int damage, int manaCost) {
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String toString() {
        return this.getClass().getName() + "a dat damage:" + damage + "si a costat mana: " + manaCost;
    }
}
