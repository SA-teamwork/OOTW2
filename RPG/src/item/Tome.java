package item;

import role.unit.Player;

public class Tome implements ItemAction {
	private int cooldown = -300;

	@Override
	public void action(Player p) {
		p.addCoolDown(cooldown);
	}

}
