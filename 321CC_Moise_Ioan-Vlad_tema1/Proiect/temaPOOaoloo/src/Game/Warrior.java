package Game;

public class Warrior extends Character {
    public int maxHealth;
    public int maxMana;

    public Warrior(String name, int experience, int level) {
        super(name, level,true, false, false);
        this.maxHealth = 100;
        this.maxMana = 100;
    }


    @Override
    public String getName() {
        return characterName;
    }

    // Atribut principal strength
    @Override
    protected int calculateBaseDamage() {
        return (strength * 3) + (dexterity * 2);
    }

    @Override
    public void receiveDamage(int damage) {
        if(Math.random() < dexterity * 0.02)
            damage /= 2;

        super.receiveDamage(damage);
    }
}
