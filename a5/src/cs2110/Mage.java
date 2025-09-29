package cs2110;

public abstract class Mage extends Player{
    public Mage(String name,GameEngine engine){
        super(name, engine);
    }

    public abstract String getSpellName();

    protected abstract void castSpell();

    @Override
    public boolean chooseAction(){
        System.out.print("Would you like to cast a " +getSpellName()+" (yes/no)? ");
        String response = engine.getInputLine();
        if (response.equalsIgnoreCase("yes")){
            castSpell();
            return false; //returning false since won't have to attack
        }
        return true;
    }

}
