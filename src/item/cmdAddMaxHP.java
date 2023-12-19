package item;

import role.unit.Player;

public class cmdAddMaxHP implements ItemCommand {

    private int hp = 80;
    private Player p;

    public cmdAddMaxHP(Player p){
        this.p = p;
    }

    @Override
    public void Execute() {
        p.addMax_HP(hp);
    }

}
