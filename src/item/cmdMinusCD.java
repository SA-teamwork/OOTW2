package item;

import role.unit.Player;

public class cmdMinusCD implements ItemCommand {
    private int cooldown = -300;

    private Player p;

    public cmdMinusCD(Player p) {
        this.p = p;
    }

    @Override
    public void Execute() {
        p.addCoolDown(cooldown);
    }

}
