package observer;

public interface PlayerChatObservable {
	public void addChatObserver(PlayerChatObserver observer);

	public void deleteChatObserver(PlayerChatObserver observer);

	public void notifyChatObservers(double posx, double posy, double delta);
}
