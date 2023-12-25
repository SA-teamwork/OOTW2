package item;

import role.unit.Player;

public class AddMaxHpCmd extends ItemCommand {

    private int hp = 80;
    private Player p;

    public AddMaxHpCmd(Player p) {
        this.p = p;
    }

    @Override
    public void Execute() {
        p.addMax_HP(hp);
    }

}
