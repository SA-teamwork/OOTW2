package observer;

import role.unit.Player;

public interface PlayerMoveObserver {
	/**
	 * 
	 * @param player
	 * @param posx
	 * @param posy
	 * @param delta
	 */
	public void action(Player player, double posx, double posy, double delta);
}
