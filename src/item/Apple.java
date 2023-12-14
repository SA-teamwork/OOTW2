package item;

import role.unit.Player;

public class Apple implements ItemAction {

	private int hp = 80;

	@Override
	public void action(Player p) {
		p.addHP(hp);
	}

}
