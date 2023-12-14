package observer;

public interface PlayerAttackObservable {
	public void addAttackObserver(PlayerAttackObserver observer);

	public void delAttackObserver(PlayerAttackObserver observer);

	/**
	 * 
	 * @param dir_x       攻擊者移動的x方向
	 * @param dir_y       攻擊者移動的y方向
	 * @param posx        攻擊者的x座標
	 * @param posy        攻擊者的y座標
	 * @param delta       Time passed since last frame (milliseconds).
	 * @param attackValue dmg
	 */
	public void notifyAttackObservers(int dir_x, int dir_y, double posx, double posy, double delta, int attackValue);
}
