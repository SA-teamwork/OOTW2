package item;

import role.unit.Player;

public class Apple implements ItemAction {

	private int hp = 80;

	@Override
	public void action(Player p) {
		p.addHP(hp);
	}

	// public Apple(Apple a) {
	// super(a);
	// this.hp = a.hp;
	// }

	// public Apple(String img_path, double posx, double posy, String name) throws
	// SlickException {
	// super(img_path, posx, posy, name, true);
	// }

	// public int getHp() {
	// return hp;
	// }
}
