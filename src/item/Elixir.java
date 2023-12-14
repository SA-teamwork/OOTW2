package item;

import role.unit.Player;

public class Elixir implements ItemAction {

	@Override
	public void action(Player p) {
		p.setElixir(true);
	}

}
