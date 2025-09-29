package cs2110;

/**
 * A subtype of Mage that specializes in a fire spell that deals double the base damage, but results in recoil damage.
 */
public class FireMage extends Mage{


    /**
     * Constructs a new FireMage with the given 'name' and game engine 'engine'.
     * Passes the name and engine to the Mage superclass.
     */
    public FireMage(String name, GameEngine engine){
        super(name,engine);
    }

    /**
     * Returns the name of this Mage's spell.
     */
    @Override
    public String getSpellName(){
        return "fire spell";
    }

    /**
     * Casts a fireball at a selected Monster. The spell inflicts double attack roll to the
     * target's 'defend()' method. The caster always takes recoil damage equal to one quarter
     * of the fireball's attack roll (rounded down).
     */
    @Override
    public void castSpell(){
        Actor target = engine.selectMonsterTarget();
        int normalAttackRoll = engine.diceRoll(1,power());
        int fireBallDamage = 2* normalAttackRoll;
        target.defend(fireBallDamage);

        int recoilDamage = fireBallDamage/4;
        this.takeDamage(recoilDamage);
    }


}
