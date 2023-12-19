package role.unit.Villager;

import observer.PlayerChatObserver;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import role.DistUtils;
import role.Main;
import role.World;
import role.unit.Player;
import role.unit.Unit;

/**
 * @author 周亮
 * @version 创建时间：2016年10月9日 上午10:45:30
 * 类说明
 * 村民角色描述类
 */
public abstract class VillagersTemplete extends Unit implements PlayerChatObserver, Cloneable {
    private String chat = "";
    private boolean chatvisible = false;
    private long chatperiod = 0L;
    private VillagersProduct vp;

    public VillagersTemplete(VillagersTemplete v) {
        super(v);
        this.chat = v.chat;
        this.chatvisible = v.chatvisible;
        this.chatperiod = v.chatperiod;
        this.vp = v.vp;
    }

    public VillagersTemplete(String image_path, double posx, double posy, int MAX_HP, int attack, int CoolDown,
                             String name)
            throws SlickException {
        super(image_path, posx, posy, MAX_HP, attack, CoolDown, 0, name);
        vp = genVillager();
    }

    public VillagersTemplete(String image_path, int tw, int th, double posx, double posy, int MAX_HP, int attack,
                             int CoolDown,
                             String name) throws SlickException {
        super(image_path, tw, th, posx, posy, MAX_HP, attack, CoolDown, 0, name, false);
        vp = genVillager();
    }

    public abstract VillagersProduct genVillager();

    @Override
    public void render(Graphics g) {
        super.render(g);
        // 绘制状态指示图标（头上方显示角色名字和健康值）
        drawNameBar(g);
        // 交谈
        if (chatvisible) {
            drawChat(g);
        }
    }

    private void drawChat(Graphics g) {
        // UnicodeFont(String ttfFileRef, int size, boolean bold, boolean italic)
        Color VALUE = new Color(1.0f, 1.0f, 1.0f); // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // Black
        int text_width = g.getFont().getWidth(this.chat);
        float bar_height = 18;
        float bar_width = text_width + 10;
        int text_x = (int) (this.getPosx() - text_width / 2);
        int text_y = (int) (this.getPosy() - bar_height - this.getHeight() / 2 - Main.NAME_BAR_HEIGHT);
        float bar_x = text_x - 5;
        float bar_y = text_y; // Size of red (HP) rectangle
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(this.chat, text_x, text_y);
    }

    @Override
    public void update(World world, int dir_x, int dir_y, int delta) {
        super.update(world, dir_x, dir_y, delta);
        // 如果正在会话，则计时。
        if (chatvisible) {
            chatperiod += delta;
            long seconds = chatperiod / 1000;
            if (seconds >= 4) {// 大于4秒结束对话
                chatvisible = false;
                chatperiod = 0L;
            }
        }
    }

    @Override
    public void chatAction(Player player, double posx, double posy, double delta) {
        // 检查player距离是否在50像素之内
        double dist = DistUtils.dist(posx, posy, this.getPosx(), this.getPosy());
        // 距离小于50像素
        if (dist <= 50) {
            // 确定交谈内容
            chat = vp.chat(player);
            // 显示会话
            chatvisible = true;
        }

    }


    abstract VillagersTemplete deepClone();


    @Override
    public VillagersTemplete clone() {
        try {
            super.clone();
            return deepClone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
