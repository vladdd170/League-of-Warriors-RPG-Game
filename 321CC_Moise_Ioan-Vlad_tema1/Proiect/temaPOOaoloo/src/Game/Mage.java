package Game;

public class Mage extends Character {
    public int maxHealth;
    public int maxMana;

    public Mage(String name, int experience, int level) {
        super(name, level,false, true, false);
        this.maxHealth = 100;
        this.maxMana = 100;
    }


    @Override
    public String getName() {
        return characterName;
    }

    // Atribut principal charisma
    @Override
    protected int calculateBaseDamage() {
        return (charisma * 3) + (strength);
    }

    @Override
    public void receiveDamage(int damage) {
        if(Math.random() < charisma * 0.03)
            damage /= 2;

        super.receiveDamage(damage);
    }
}
