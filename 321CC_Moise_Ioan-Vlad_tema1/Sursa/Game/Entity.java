package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.*;

public abstract class Entity implements Battle {
    protected List<Objects> abilities;
    protected int currentHealth;
    protected int maxHealth;
    protected int currentMana;
    protected int maxMana;

    protected boolean fireImmunity;
    protected boolean iceImmunity;
    protected boolean earthImmunity;

    public Entity(boolean fireImmunity, boolean iceImmunity, boolean earthImmunity) {
        this.maxHealth = 100;
        this.maxMana = 100;
        this.fireImmunity = fireImmunity;
        this.iceImmunity = iceImmunity;
        this.earthImmunity = earthImmunity;
        this.abilities = new ArrayList<>();
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
    }

    public void regenerateHealth(int value) {
        // Se opreste viata la maximum
        currentHealth = currentHealth + value;
        if (this.currentHealth > maxHealth) {
            this.currentHealth = maxHealth;
        }
    }

    public void regenerateMana(int value) {
        currentMana = currentMana + value;
        if (this.currentMana > maxMana) {
            this.currentMana = maxMana;
        }
    }

    //TBE
    public void useAbility(Objects ability, Entity enemy) {
        System.out.println("S-a folosit abilitatea Gigel");
    }

    public boolean isImmuneTo(Object obj) {
        System.out.println("Este imun la Gigel");
        return false;
    }

    @Override
    public void receiveDamage(int damage) {
        if (Math.random() < 0.5) {
            damage = damage / 2;
        }

        currentHealth = Math.max(currentHealth - damage, 0);
        System.out.println(getName() + " a primit " + damage + " Damage! Viata ramasa = " + currentHealth + " hp\n" );
    }

    @Override
    public int getDamage() {
        int baseDamage = calculateBaseDamage();
        if ( Math.random() < 0.5 ) {
            baseDamage = baseDamage * 2;
        }
        return baseDamage;
    }

    protected abstract int calculateBaseDamage();

    public abstract String getName();

    public void addAbility(Objects ability) {
        abilities.add(ability);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }
}
