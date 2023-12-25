package item;

import role.unit.Player;

public class AddHpCmd extends ItemCommand {

    private int hp = 80;
    private Player p;

    public AddHpCmd(Player p) {
        this.p = p;
    }

    @Override
    public void Execute() {
        p.addHP(hp);
    }

}
