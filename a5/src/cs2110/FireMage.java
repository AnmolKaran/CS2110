package cs2110;

public class FireMage extends Mage{

    public FireMage(String name, GameEngine engine){
        super(name,engine);
    }
    @Override
    public String getSpellName(){
        return "fire spell";
    }

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
