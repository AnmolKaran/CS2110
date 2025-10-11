package cs2110;

/**
 * A type of Mage that uses a supportive spell to heal Players.
 */
public class Healer extends Mage {

    /**
     * Constructs a new Healer with the given 'name' and game engine 'engine'.
     * Passes the name and engine to the Mage superclass.
     */
    public Healer(String name, GameEngine engine) {
        super(name, engine);
    }

    /**
     * Returns the name of this Mage's spell.
     */
    @Override
    public String getSpellName() {
        return "healing spell";
    }

    /**
     * Casts a healing spell on a selected Player. The amount of health restored is a random
     * integer number between 0 and the Healer's current power level (inclusive).
     */
    @Override
    public void castSpell() {
        Actor playerToHeal = engine.selectPlayerTarget();
        int pointsToRestore = engine.diceRoll(0, power());
        playerToHeal.heal(pointsToRestore);
    }


}
