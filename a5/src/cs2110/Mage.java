package cs2110;


/**
 * An abstract class for a Player that can cast spells. It defines the
 * standard structure for all Mages (i.e. casting a spell).
 */
public abstract class Mage extends Player {

    /**
     * Constructs a new Mage with the given 'name' and engine.
     * Passes the name and engine to the Player superclass.
     */
    public Mage(String name, GameEngine engine) {
        super(name, engine);
    }

    /**
     * Returns the specific name of the Mage's spell.
     */
    public abstract String getSpellName();


    /**
     * Defines the unique behavior of the spell
     * and perform the actions of the subclass's respective spell.
     */
    protected abstract void castSpell();


    /**
     * Implements the standard turn logic for all Mages. It prompts the user to cast
     * their spell. If they choose to do so, the spell is cast, and the turn ends. Otherwise, the turn proceeds to an attack phase.
     * Returns 'false' if a spell was cast (which ends the turn), 'true' otherwise (proceeding to attack)
     */
    @Override
    public boolean chooseAction() {
        System.out.print("Would you like to cast a " + getSpellName() + " (yes/no)? ");
        String response = engine.getInputLine();
        if (response.equalsIgnoreCase("yes")) {
            castSpell();
            return false; //returning false since won't have to attack
        }
        return true;
    }

}
