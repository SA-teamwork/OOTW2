package item;

import role.unit.Player;

public class cmdAddHP implements ItemCommand {

    private int hp = 80;
    private Player p;

    public cmdAddHP(Player p) {
        this.p = p;
    }

    @Override
    public void Execute() {
        p.addHP(hp);
    }

}
