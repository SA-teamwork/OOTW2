package item;

import role.unit.Player;

public class AddAtkCmd extends ItemCommand {

    private int damage = 30;

    private Player p;

    public AddAtkCmd(Player p) {
        this.p = p;
    }

    @Override
    public void Execute() {
        p.addAttack(damage);
    }
}
