package item;

import role.unit.Player;

public class SetElixirCmd extends ItemCommand {

    private Player p;

    public SetElixirCmd(Player p) {
        this.p = p;
    }

    @Override
    public void Execute() {
        p.setElixir(true);
    }

}
