package Game;

import java.util.*;

public abstract class Character extends Entity {
    protected String characterName;
    protected int characterXP;
    protected int level;
    protected int strength;
    protected int charisma;
    protected int dexterity;
    private int gamesPlayed;
    private Game gameInstance;
    private List<Spell> abilities;

    public Character(String name, int level, boolean fireImmunity, boolean iceImmunity, boolean earthImmunity) {
        super(fireImmunity, iceImmunity, earthImmunity);
        this.characterName = name;
        this.level = level;
        this.characterXP = characterXP;
        this.strength = 5;
        this.charisma = 5;
        this.dexterity = 5;
        this.maxHealth = 100;
        this.maxMana = 100;
        this.gamesPlayed = 0;
        this.abilities = generateAbilities();
    }

    public void reduceMana(int mana) {
        this.currentMana -= mana;
        if(this.currentMana <= 0) {
            this.currentMana = 0;
        }
    }

    public List<Spell> getAbilities() {
        return abilities;
    }

    private List<Spell> generateAbilities() {
        List<Spell> abilities = new ArrayList<>();

        abilities.add(new Ice(30, 25));
        abilities.add(new Fire(40, 40));
        abilities.add(new Earth(35, 25));

        return abilities;
    }

    public void refreshAbilities() {
        this.abilities = generateAbilities();
    }

    public void increaseGamesPlayed() {
        this.gamesPlayed++;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void enterSanctuary() {
        Random rand = new Random();
        regenerateHealth(rand.nextInt(40) + 20);
        regenerateMana(rand.nextInt(30) + 20);
    }

    public void addExperience(int exp) {
        this.characterXP += exp;
        checkLevelUp();
    }

    public void checkLevelUp() {
        int threshold = level * 10;
        while (characterXP >= threshold) {
            characterXP -= threshold;
            level++;
            upgradeAttributes();
            threshold = level * 10;
        }
    }

    public void upgradeAttributes() {
        strength += 2;
        charisma += 1;
        dexterity += 2;
    }

    public void resetHealthAndMana() {
        currentHealth = maxHealth;
        currentMana = maxMana;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    @Override
    protected int calculateBaseDamage() {
        return strength * 2 + dexterity;
    }

    public void receiveDamage(int damage) {
        // 1% reduction per charisma point
        if(Math.random() < charisma * 0.02) {
            damage /= 2;
        }

        this.currentHealth -= damage;
        System.out.println(this.getName() + " a primit " + damage + " Damage! Viata ramasa = " + currentHealth + " hp\n" );
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getCharacterXP() {
        return characterXP;
    }

    public void setCharacterXP(int characterXP) {
        this.characterXP = characterXP;
    }

    public int getCharacterLevel() {
        return level;
    }

    public void setCharacterLevel(int characterLevel) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setGameInstance(Game game) {
        this.gameInstance = game;
    }

    public Game getGameInstance() {
        return gameInstance;
    }
}
