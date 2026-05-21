package Game;

public class Rogue extends Character {
    int maxHealth, maxMana;

    public Rogue(String name, int experience, int level) {
        super(name, level,false, false, true);
        maxHealth = 100;
        maxMana = 100;
    }

    @Override
    public String getName() {
        return characterName;
    }

    // Atribut principal dexterity
    @Override
    protected int calculateBaseDamage() {
        return (dexterity * 3) + (charisma);
    }

    @Override
    public void receiveDamage(int damage) {
        if(Math.random() < dexterity * 0.03)
            damage /= 2;

        super.receiveDamage(damage);
    }
}
