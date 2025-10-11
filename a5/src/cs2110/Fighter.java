package cs2110;

import java.util.Scanner;


/**
 * A type of Player that can equip a Weapon to modify its power and toughness stats.
 */
public class Fighter extends Player {

    /**
     * The weapon the Fighter has equipped. It can be null if no weapon is equipped.
     */
    private Weapon equippedWeapon;

    /**
     * Constructs a new Fighter with the given 'name' associated
     * with the Game Engine 'engine' that created it. A Fighter starts with no weapon equipped.
     */
    public Fighter(String name, GameEngine engine) {
        super(name, engine);
        this.equippedWeapon = null;
    }


    /**
     * Asks the user if they want to change equipment and handles the logic for swapping weapons.
     * This method overrides the general behavior of a Player's turn.
     * Returns 'true' always, as a Fighter proceeds to the attack phase
     * regardless of their choice.
     */
    @Override
    public boolean chooseAction() {


        System.out.print("Would you like to change your current equipment (yes/no)? ");
        String response = engine.getInputLine();
        if (response.equalsIgnoreCase("yes")) {
            Weapon chosenWeapon = engine.selectWeapon();
            if (this.equippedWeapon != null) {
                this.equippedWeapon.unequip();
            }
            this.equippedWeapon = chosenWeapon;
            // chosenWeapon can be null
            if (this.equippedWeapon != null) {
                this.equippedWeapon.equip();

            }
            return true;
        }
        return true;
    }


    /**
     * Returns the Fighter's total power, which is calculated
     * by adding the equipped weapon's power to the Fighter's base power.
     */
    @Override
    public int power() {
        int weaponPower = 0;
        if (equippedWeapon != null) {
            weaponPower = equippedWeapon.power();

        }
        return super.power() + weaponPower;

    }

    /**
     * Returns the Fighter's total toughness, which is calculated
     * by adding the equipped weapon's toughness to the FIghter's base toughness.
     */
    @Override
    public int toughness() {
        int weaponToughness = 0;
        if (equippedWeapon != null) {
            weaponToughness = equippedWeapon.toughness();
        }
        return super.toughness() + weaponToughness;
    }

    /**
     * Handles the Fighter's death. Ensures that the Fighter drops its weapon,
     * making it available again, before the regular Player death process occurs.
     */
    @Override
    protected void processDeath() {
        if (equippedWeapon != null) {
            //System.out.println();
            equippedWeapon.unequip();
            equippedWeapon = null;
        }

        super.processDeath();
    }
}
