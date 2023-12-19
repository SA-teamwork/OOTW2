package item;

import role.unit.Player;

public class cmdSetElixir implements ItemCommand {

	private Player p;

	public cmdSetElixir(Player p){
		this.p = p;
	}

	@Override
	public void Execute() {
		p.setElixir(true);
	}

}
