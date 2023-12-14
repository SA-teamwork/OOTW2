package observer;

public interface PlayerMoveObservable {

	public void addMoveObserver(PlayerMoveObserver observer);

	public void deleteMoveObserver(PlayerMoveObserver observer);

	public void notifyObservers(double posx, double posy, double delta);
}
