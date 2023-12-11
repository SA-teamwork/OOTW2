package role.unit.monster;

import java.util.Random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import item.DistUtils;
import observer.PlayerAttackObserver;
import role.World;
import role.unit.Player;
import role.unit.Unit;

public class PassiveMonster extends Unit implements PlayerAttackObserver {
	private static double SPEED = 0.2;

	private boolean safe = true;
	private long directChagetimer = 0L;
	private long unsafePeriodTimer = 0L;
	private int dir_y = 0;
	private int dir_x = 0;
	private Random dirRdm;

	public PassiveMonster(PassiveMonster p) {
		super(p);
		this.safe = p.safe;
		this.directChagetimer = p.directChagetimer;
		this.unsafePeriodTimer = p.unsafePeriodTimer;
		this.dir_y = p.dir_y;
		this.dir_x = p.dir_x;
		this.dirRdm = new Random();
	}

	public PassiveMonster(String image_path, double posx, double posy, int MAX_HP, int attack, int CoolDown,
			long resurgence, String name)
			throws SlickException {
		super(image_path, posx, posy, MAX_HP, attack, CoolDown, resurgence, name);
		dirRdm = new Random();
	}

	public PassiveMonster(String image_path, int tw, int th, double posx, double posy, int MAX_HP, int attack,
			int CoolDown,
			long resurgence, String name)
			throws SlickException {
		super(image_path, tw, th, posx, posy, MAX_HP, attack, CoolDown, resurgence, name, false);
		dirRdm = new Random();
	}

	public void move(World world, Player player, double delta) {
		/**
		 * position befor move
		 */
		double pre_x = this.getPosx();
		double pre_y = this.getPosy();

		if (safe) { // ramdom move
			calculateNewCoordinate(world, dir_x, dir_y, SPEED, delta);

			// get the direction
			if (directChagetimer == 0L) {
				// change direction
				dir_y = dirRdm.nextInt(3) - 1;// -1 to 1
				dir_x = dirRdm.nextInt(3) - 1;// -1 to 1
				directChagetimer += delta;
			} else {
				directChagetimer += delta;
				long seconds = directChagetimer / 1000;
				if (seconds >= 3)
					directChagetimer = 0L;
			}
		} else { // run a way~
			calculateAttackMonsterCoordinate(world, SPEED, delta, player.getPosx(), player.getPosy(), true);

			// 如果沒有度過安全期，還是不安全的
			unsafePeriodTimer += delta;
			long seconds = unsafePeriodTimer / 1000;
			if (seconds >= 5) // 渡過5秒安全期,變成安全狀態，可以自由移動
				safe = true;

			/*
			 * 對比前後位移是否變化從而判斷是否遇到障礙物，因為逃離時的dir x和dir y不會取0，所以除了遇到障礙物外不可能原地不動
			 * 不安全期如果遇到障礙物要自動更換方向,位置沒有變則遇到障礙物
			 */
			if (pre_x == this.getPosx() && pre_y == this.getPosy()) {
				dir_x = dirRdm.nextInt(3) - 1;// -1 to 1
				dir_y = dirRdm.nextInt(3) - 1;// -1 to 1
				calculateNewCoordinate(world, dir_x, dir_y, SPEED, delta);
			}
		}
	}

	public void update(World world, int dir_x, int dir_y, int delta, Player player) {
		super.update(world, dir_x, dir_y, delta);
		// 1.此處使用!this.isDieOut()方法可以讓沒有滅絕的角色的復活地點不在原地
		if (this.isActive()) {
			move(world, player, delta);
		}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		if (this.isActive()) {
			// 繪製狀態指示圖示（頭上方顯示角色名字和健康值）
			this.drawNameBar(g);
		}
	}

	/**
	 * 監聽player攻擊行為
	 */
	@Override
	public void handleAttack(int dir_x, int dir_y, double posx, double posy, double delta, int attackValue) {
		// 如果角色沒滅絕，被玩家攻擊
		if (this.isActive()) {
			// 計算和player的距離
			double dist = DistUtils.dist(this.getPosx(), this.getPosy(), posx, posy);
			if (dist <= super.getWidth() * 1.2) {// 在被攻擊範圍
				// 不安全
				safe = false;
				// -hp
				this.cutHp(attackValue);
				// 如果被打死則進入安全期
				if (this.isDieout()) {
					safe = true;
				} else {
					// 不安全期計時器設為0
					unsafePeriodTimer = 0L;
				}
			}
		}
	}
}
