package item;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import observer.PlayerMoveObserver;
import role.unit.Player;

public class Item implements PlayerMoveObserver {
	private Image img;
	private double posx;
	private double posy;
	private String name;
	private boolean visible;
	private boolean immediate;
	private ItemAction ia;
	// TODO: 抽出-介面

	public Item(Item i) {
		img = i.img.copy();
		posx = i.posx;
		posy = i.posy;
		name = i.name;
		visible = i.visible;
		immediate = i.immediate;
		ia = i.ia;
	}

	public Item(String img_path, double posx, double posy, String name)
			throws SlickException {
		this.img = new Image(img_path);
		this.posx = posx;
		this.posy = posy;
		this.name = name;
		this.visible = true;
		this.immediate = false;
		this.ia = ItemActionInit();

	}

	public Item(String img_path, double posx, double posy, String name, Boolean immediate)
			throws SlickException {
		this.img = new Image(img_path);
		this.posx = posx;
		this.posy = posy;
		this.name = name;
		this.visible = true;
		this.immediate = immediate;
		this.ia = ItemActionInit();
	}

	private ItemAction ItemActionInit() {
		switch (name) {
			default:
				return new Apple();
			case "amulet":
				return new Amulet();
			case "sword":
				return new Sword();
			case "tome":
				return new Tome();
			case "elixir":
				return new Elixir();
			case "apple":
				return new Apple();
		}
	}

	public String getName() {
		return name;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public double getX() {
		return posx;
	}

	public double getY() {
		return posy;
	}

	public Image getImage() {
		return img;
	}

	public void render(Graphics g) {
		if (this.img != null && this.isVisible())
			img.drawCentered((int) posx, (int) posy);
	}

	public void action(Player player, double posx, double posy, double delta) {
		// 如果物品可见
		if (isVisible()) {
			// 距离50像素
			double dist = DistUtils.dist(posx, posy, this.posx, this.posy);
			if (dist <= 20) {
				// System.out.println("pick!!");
				// player get the item
				if (immediate) {
					player.putItem(this);
				}
				ia.action(player);
				setVisible(false);
			}
		}
	}

}
