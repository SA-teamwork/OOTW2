package observer;

import role.unit.Player;

public interface PlayerChatObserver {
	public void chatAction(Player player, double posx, double posy, double delta);
}
