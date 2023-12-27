package item;

import observer.PlayerMoveObserver;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import role.DistUtils;
import role.unit.Player;

public class Item implements PlayerMoveObserver {
    private Image img;
    private double posx;
    private double posy;
    private String name;
    private boolean visible;
    private boolean immediate;
    private ItemCommand ia;

    public Item(Item i, Player p) {
        img = i.img.copy();
        posx = i.posx;
        posy = i.posy;
        name = i.name;
        visible = i.visible;
        immediate = i.immediate;
        ia = i.ia;
    }

    public Item(String img_path, double posx, double posy, String name, Player p) throws SlickException {
        this.img = new Image(img_path);
        this.posx = posx;
        this.posy = posy;
        this.name = name;
        this.visible = true;
        this.immediate = false;
        this.ia = ItemActionInit(p);

    }

    public Item(String img_path, double posx, double posy, String name, Boolean immediate, Player p) throws SlickException {
        this.img = new Image(img_path);
        this.posx = posx;
        this.posy = posy;
        this.name = name;
        this.visible = true;
        this.immediate = immediate;
        this.ia = ItemActionInit(p);
    }

    private ItemCommand ItemActionInit(Player p) {
        switch (name) {
            case "amulet" -> {
                CompositeCmd cmd = new CompositeCmd();
                cmd.addCmd(new AddMaxHpCmd(p));
                cmd.addCmd(new AddHpCmd(p));
                return cmd;
            }
            case "sword" -> {
                return new AddAtkCmd(p);
            }
            case "tome" -> {
                return new MinusCdCmd(p);
            }
            case "elixir" -> {
                return new SetElixirCmd(p);
            }
            case "apple" -> {
                return new AddHpCmd(p);
            }
            default -> {
                return null;
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isImmediate() {
        return immediate;
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
        if (this.img != null && this.isVisible()) img.drawCentered((int) posx, (int) posy);
    }

    public void action(Player player, double posx, double posy, double delta) {
        if (player == null) {
            throw new NullPointerException("player Can NOT be 'null'");
        }
        // 如果物品可见
        if (isVisible()) {
            // 距离50像素
            double dist = DistUtils.dist(posx, posy, this.posx, this.posy);
            if (dist <= 20) {
                System.out.println("pick!!");
                // player get the item
                if (!immediate) {
                    player.putItem(this);
                }
                ia.Execute();
                setVisible(false);
            }
        }
    }

}
