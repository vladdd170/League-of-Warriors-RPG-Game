package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Entity {
    private int baseDamage;
    private List<Spell> abilities;
    private int currentMana, currentHealth;

    public Enemy() {
        super(new Random().nextBoolean(), new Random().nextBoolean(), new Random().nextBoolean());
        this.maxHealth = 100;
        this.maxMana = 100;
        this.currentMana = maxMana;
        this.currentHealth = maxHealth;

        this.abilities = generateAblities();
        this.baseDamage = calculateBaseDamage();
    }

    public List<Spell> generateAblities() {
        List<Spell> abilities = new ArrayList<>();
        Random random = new Random();
        int nrAbilities = random.nextInt(4) + 3;
        for (int i = 0; i < nrAbilities; i++) {
            abilities.add(generateRandomAbility());
        }
        return abilities;
    }

    public void reduceMana(int mana) {
        this.currentMana -= mana;
        if (this.currentMana < 0) {
            this.currentMana = 0;
        }
    }

    public List<Spell> getAbilities() {
        return abilities;
    }

    public int calculateBaseDamage() {
        Random random = new Random();
        this.baseDamage = random.nextInt(15) + 5; // intre 5 si 20 damage-ul basic
        return this.baseDamage;
    }

    private Spell generateRandomAbility() {
        Random random = new Random();
        int damage = random.nextInt(30) + 5;
        int mana = random.nextInt(30) + 5;

        switch (random.nextInt(3)) {
            case 0:
                return new Ice(damage, mana);
            case 1:
                return new Fire(damage, mana);
            case 2:
                return new Earth(damage, mana);
        }
        return null;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    // 50% dodge la damage
    @Override
    public void receiveDamage(int damage) {
        if (Math.random() < 0.5) {
            damage /= 2;
        }
        this.currentHealth -= damage;
        System.out.println(this.getName() + " a primit " + damage + " Damage! Viata ramasa = " + currentHealth + " hp\n" );
    }

    // 50% damage OP
    @Override
    public int getDamage() {
        if (Math.random() < 0.5) {
            return baseDamage * 2;
        }
        return baseDamage;
    }

    @Override
    public String getName() {
        return "Enemy";
    }
}
