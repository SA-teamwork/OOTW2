package item;

import role.unit.Player;

public class Amulet implements ItemAction {

	private int hp = 80;

	// public int getHp() {
	// return hp;
	// }

	@Override
	public void action(Player p) {
		p.addMax_HP(hp);
	}

}
