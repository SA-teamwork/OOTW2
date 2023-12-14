package item;

import role.unit.Player;

public class Sword implements ItemAction {

	private int damage = 30;

	@Override
	public void action(Player p) {
		p.addAttack(damage);
	}
}
