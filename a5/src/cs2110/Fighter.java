package cs2110;

import java.util.Scanner;


//Todo: write invariants for class methods, also specifications i think
public class Fighter extends Player{
    private Weapon equippedWeapon;

    public Fighter(String name,GameEngine engine){
        super(name, engine);
        this.equippedWeapon = null;
    }

    @Override
    public boolean chooseAction() {


        System.out.print("Would you like to change your current equipment (yes/no)? ");
        String response = engine.getInputLine();
        if (response.equalsIgnoreCase("yes")){
            Weapon chosenWeapon = engine.selectWeapon();
            if (this.equippedWeapon!= null){
                this.equippedWeapon.unequip();
            }
            this.equippedWeapon = chosenWeapon;
            // chosenWeapon can be null
            if (this.equippedWeapon != null){
                this.equippedWeapon.equip();

            }else{
                //TODO: make sure this right
                System.out.println("Unequipped");
            }
            return true;
        }
        return true;
    }

    @Override
    public int power(){
        int weaponPower = 0;
        if (equippedWeapon != null){
            weaponPower = equippedWeapon.power();

        }
        return super.power() + weaponPower;

    }

    @Override
    public int toughness(){
        int weaponToughness = 0;
        if (equippedWeapon!=null){
            weaponToughness = equippedWeapon.toughness();
        }
        return super.toughness() + weaponToughness;
    }

    @Override
    protected void processDeath(){
        if (equippedWeapon != null){
            //System.out.println();
            equippedWeapon.unequip();
            equippedWeapon = null;
        }

        super.processDeath();
    }
}
