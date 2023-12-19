package item;

import role.unit.Player;

public class cmdAddATK implements ItemCommand {

	private int damage = 30;

	private Player p;

	public cmdAddATK(Player p){
		this.p = p;
	}

	@Override
	public void Execute() {
		p.addAttack(damage);
	}
}
