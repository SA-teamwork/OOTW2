package item;

import role.unit.Player;

public class MinusCdCmd extends ItemCommand {
    private int cooldown = -300;

    private Player p;

    public MinusCdCmd(Player p) {
        this.p = p;
    }

    @Override
    public void Execute() {
        p.addCoolDown(cooldown);
    }

}
