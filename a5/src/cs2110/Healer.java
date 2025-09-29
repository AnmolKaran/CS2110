package cs2110;

public class Healer extends Mage {

    public Healer(String name, GameEngine engine){
        super(name,engine);
    }
    @Override
    public String getSpellName(){
        return "healing spell";
    }

    @Override
    public void castSpell(){
        Actor playerToHeal = engine.selectPlayerTarget();
        int pointsToRestore = engine.diceRoll(0,power());
        playerToHeal.heal(pointsToRestore);
    }


}
