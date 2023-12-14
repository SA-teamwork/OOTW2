package role.unit;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import role.Main;
import role.World;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;

public class Unit {
    // In pixels
    private double posx;
    private double posy;
    private int CoolDown;
    private int HP;
    private int MAX_HP;
    private int attack;
    private int coolDownTime = 30;
    private Image img = null;
    private Image img_flipped = null;
    private double width, height;
    private boolean face_left = false;
    private String name;
    // private String image_path;
    private boolean isMove = false;
    private boolean atkble = false;

    private List<Animation> anima = new ArrayList<>();
    private List<Animation> anima_flipped = new ArrayList<>();

    /**
     * 判斷角色是否滅絕，角色一旦滅絕就不會復活，
     * 如果角色沒有滅絕，當角色hp為0時，在復活等待期到達後則可以復活。
     * 目前，只有player可以復活。
     */
    private boolean isdieout = false;
    /**
     * 角色復活等待時間,in mills
     */
    private long resurgence = 0L;
    /**
     * 復活計時器，角色死亡後進入復活期，當計時器為0時滿血復活
     */
    private long resurgenceTimer = 0L;

    public Unit(Unit u) {
        this.posx = u.posx;
        this.posy = u.posy;
        this.CoolDown = u.CoolDown;
        this.HP = u.HP;
        this.MAX_HP = u.MAX_HP;
        this.attack = u.attack;
        this.coolDownTime = u.coolDownTime;

        if (u.img != null) {
            this.img = u.img.copy();
            this.img_flipped = u.img_flipped.copy();
        }

        this.width = u.width;
        this.height = u.height;
        this.face_left = u.face_left;
        this.name = u.name;
        this.isMove = u.isMove;
        this.atkble = u.atkble;

        if (!u.anima.isEmpty()) {
            this.anima = new ArrayList<>();
            for (Animation a : u.anima) {
                a = a.copy();
                a.restart();
                this.anima.add(a);
            }

            this.anima_flipped = new ArrayList<>();
            for (Animation a : u.anima_flipped) {
                a = a.copy();
                a.restart();
                this.anima_flipped.add(a);
            }
        }

        this.isdieout = u.isdieout;
        this.resurgence = u.resurgence;
        this.resurgenceTimer = u.resurgenceTimer;
    }

    public Unit(String image_path, double posx, double posy, int MAX_HP, int attack, int CoolDown, long resurgence,
            String name)
            throws SlickException {
        img = new Image(image_path);
        img_flipped = img.getFlippedCopy(true, false);
        this.posx = posx;
        this.posy = posy;
        this.width = img.getWidth();
        this.height = img.getHeight();
        this.HP = MAX_HP;
        this.MAX_HP = MAX_HP;
        this.attack = attack;
        this.CoolDown = CoolDown;
        this.name = name;
        this.resurgence = resurgence;
    }

    public Unit(String image_path, int tw, int th, double posx, double posy, int MAX_HP, int attack, int CoolDown,
            long resurgence, String name, boolean atkble)
            throws SlickException {

        String[] staticImg;
        if (atkble) {
            staticImg = new String[] {
                    image_path + "static.png",
                    image_path + "move.png",
                    image_path + "die.png",
                    image_path + "atk.png"
            };
        } else {
            staticImg = new String[] {
                    image_path + "static.png",
                    image_path + "move.png",
                    image_path + "die.png",
                    // image_path + "atk.png"
            };
        }

        // List<SpriteSheet> img = new ArrayList<SpriteSheet>();
        // List<SpriteSheet> img_flipped = new ArrayList<SpriteSheet>();
        for (String string : staticImg) {
            Animation a = new Animation(new SpriteSheet(string, tw, th), 100);

            Animation a_f = new Animation();
            for (int i = 0; i < a.getFrameCount(); i++) {
                a_f.addFrame(a.getImage(i).getFlippedCopy(true, false), 100);
            }

            if (string.contains("die.png")) {
                a.setLooping(false);
                a_f.setLooping(false);

            } else if (string.contains("atk.png")) {
                a.setLooping(false);
                a.setSpeed(3.0f);
                a_f.setLooping(false);
                a_f.setSpeed(3.0f);
            }

            anima.add(a);
            anima_flipped.add(a_f);
        }

        this.posx = posx;
        this.posy = posy;
        this.width = tw;
        this.height = th;
        this.HP = MAX_HP;
        this.MAX_HP = MAX_HP;
        this.attack = attack;
        this.CoolDown = CoolDown;
        this.name = name;
        this.resurgence = resurgence;
        this.atkble = atkble;
    }

    public String getName() {
        return name;
    }

    public boolean isDieout() {
        return isdieout;
    }

    public void setIsdieout(boolean isdieout) {
        this.isdieout = isdieout;
    }

    public long getResurgenceTimer() {
        return resurgenceTimer;
    }

    public long getResurgence() {
        return resurgence;
    }

    public double getPosx() {
        return posx;
    }

    public double getPosy() {
        return posy;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public int getCoolDown() {
        return CoolDown;
    }

    public int getHP() {
        return HP;
    }

    public void addMax_HP(int hp) {
        this.MAX_HP += hp;
        this.HP += hp;
    }

    public void addHP(int hp) {
        if ((this.HP + hp) > this.MAX_HP) {
            this.HP = this.MAX_HP;
        } else {
            this.HP += hp;
        }
    }

    public void addAttack(int attack) {
        this.attack += attack;
    }

    public void addCoolDown(int CoolDown) {
        this.CoolDown += CoolDown;
    }

    public int getAttack() {
        return attack;
    }

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public void startCoolDownTime() {
        this.coolDownTime = CoolDown;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    private void renderWithoutAnima() {
        if (this.isActive()) {
            Image which_img;
            which_img = this.face_left ? this.img_flipped : this.img;
            which_img.drawCentered((int) posx, (int) posy);
        }
    }

    private void renderDirectly(int i) {
        if (this.face_left) {
            this.anima_flipped.get(i).draw((int) (posx - (width / 2)), (int) (posy - (height / 2)));
        } else {
            this.anima.get(i).draw((int) (posx - (width / 2)), (int) (posy - (height / 2)));
        }
    }

    private void renderMoveAnima() {
        if (!this.isMove) {
            renderDirectly(0);
        } else {
            renderDirectly(1);
        }
    }

    private void renderAnima_CanAtk() {
        if (this.isActive()) {
            if (this.getCoolDownTime() <= 0) {
                this.anima.get(3).restart();
                this.anima_flipped.get(3).restart();
                renderMoveAnima();
            } else if (this.getCoolDownTime() > 0) {
                renderDirectly(3);
            }
        } else if (!this.anima_flipped.get(2).isStopped() && !this.anima.get(2).isStopped()) {
            renderDirectly(2);
        }
    }

    private void renderAnima_CanNotAtk() {
        if (this.isActive()) {
            renderMoveAnima();
        } else if (!this.anima_flipped.get(2).isStopped() && !this.anima.get(2).isStopped()) {
            renderDirectly(2);
        }
    }

    /**
     * Draw the player to the screen at the correct place.
     * 
     * @param g     The current Graphics context.
     */
    public void render(Graphics g) {
        if (anima.size() == 0) {
            renderWithoutAnima();
        } else if (this.anima.size() == 3) {
            renderAnima_CanNotAtk();
        } else if (this.anima.size() == 4) {
            renderAnima_CanAtk();
        }
    }

    /**
     * -hp
     */
    public void cutHp(int attackValue) {
        if (this.HP > 0) {
            if (attackValue > 0) {
                int leftHp = this.HP - attackValue;
                this.HP = Math.max(leftHp, 0);
                if (this.HP <= 0) {
                    // 死亡
                    death();
                }
            }
        }
    }

    /**
     * 判斷角色是否活躍，角色活躍則可以在地圖上進行正常的角色活動，否則消失在地圖上。
     * 1.如果角色已經滅絕isdeadout=true，則角色死亡。
     * 2.isdeadout=false,resurgenceTimer>0，則角色死亡。
     * 3.isdeadout=false,resurgenceTimer<=0,角色活躍著。
     *
     */
    public boolean isActive() {
        if (!this.isdieout && resurgenceTimer <= 0)
            return true;
        else
            return false;
    }

    public void update(World world, int dir_x, int dir_y, int delta) {
        // 如果沒死透
        if (!this.isdieout && this.resurgenceTimer >= 0) {
            this.resurgenceTimer -= delta;
            if (this.resurgenceTimer <= 0) {
                comeBack();
            }
        } else if (this.isActive() && this.coolDownTime > 0) {
            this.coolDownTime -= delta;
        }
    }

    /**
     * 復活
     */
    public void comeBack() {
        this.HP = this.MAX_HP;
        this.coolDownTime = 0;
        try {
            this.anima.get(2).restart();
            this.anima.get(3).restart();
            this.anima_flipped.get(2).restart();
            this.anima_flipped.get(3).restart();
        } catch (Exception ignored) {
        }
    }

    /**
     * 死亡
     */
    public void death() {
        // 判斷是否可以復活, resurgence>=0 不可以復活
        if (this.resurgence >= 0) {
            this.resurgenceTimer = this.resurgence;
            // System.out.println(this.name + " is die, it will resurgence");
        } else
            this.isdieout = true;
        // System.out.println(this.name + " is die");
    }

    public double getPercentage() {
        return (double) HP / (double) MAX_HP;
    }

    public double getCooldownPercentage() {
        return (double) coolDownTime / (double) CoolDown;
    }

    /**
     * implement the Algorithm 1
     * 用於計算monster被player攻擊或和player戰鬥時移動的座標
     *
     * @param world
     * @param speed    速度
     * @param delta    delta Time passed since last frame (milliseconds).
     * @param player_x player的x座標
     * @param player_y player的y座標
     * @param isflee   true:表示monster逃離player；false:monster靠近player
     */
    public void calculateAttackMonsterCoordinate(World world, double speed, double delta, double player_x,
            double player_y, boolean isflee) {

        double p_x = this.posx, p_y = this.posy;
        // 使用計算座標移動距離指令計算新的座標移動距離
        double amount = delta * speed;
        double distx = player_x - this.posx;
        double disty = player_y - this.posy;
        double dir_x = 0L;
        double dir_y = 0L;
        // 靠近player
        if (!isflee) {
            dir_x = distx > 0 ? 1 : (distx < 0 ? -1 : 0);
            dir_y = disty > 0 ? 1 : (disty < 0 ? -1 : 0);
        } else {
            // RUN!!

            dir_x = distx >= 0 ? -1 : 1;
            dir_y = disty >= 0 ? -1 : 1;
            distx = -distx;
            disty = -disty;
        }
        if (dir_x > 0)
            this.face_left = false;
        else if (dir_x < 0)
            this.face_left = true;

        double dist_total = Math.pow((Math.pow(distx, 2) + Math.pow(disty, 2)), 0.5);
        double dx = distx / dist_total * amount;
        double dy = disty / dist_total * amount;

        double new_x = posx + dx;
        double new_y = posy + dy;
        // Move in x first
        double x_sign = Math.signum(dir_x);
        if (!world.terrainBlocks(new_x + x_sign * width / 4, posy + height / 4)
                && !world.terrainBlocks(new_x + x_sign * width / 4, posy - height / 4)) {
            posx = new_x;
        }
        // Then move in y
        double y_sign = Math.signum(dir_y);
        if (!world.terrainBlocks(posx + width / 4, new_y + y_sign * height / 4)
                && !world.terrainBlocks(posx - width / 4, new_y + y_sign * height / 4)) {
            posy = new_y;
        }

        if (posx == p_x && posy == p_y) {
            this.isMove = false;
        } else {
            this.isMove = true;
        }
    }

    /**
     * 計算新的座標
     * 
     * @param dir_x The unit's movement in the x axis (-1, 0 or 1).
     * @param dir_y The unit's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void calculateNewCoordinate(World world, double dir_x, double dir_y, double speed, double delta) {
        double p_x = this.posx, p_y = this.posy;

        if (dir_x > 0)
            this.face_left = false;
        else if (dir_x < 0)
            this.face_left = true;

        // Move the monster automtic, as a multiple of delta * speed
        double amount = delta * speed;
        double new_x = posx + dir_x * amount;
        double new_y = posy + dir_y * amount;
        // System.out.println(dir_x+", "+dir_y);
        // Move in x first
        double x_sign = Math.signum(dir_x);
        int a = 4;
        if (!world.terrainBlocks(new_x + x_sign * width / a, posy + height / a)
                && !world.terrainBlocks(new_x + x_sign * width / a, posy - height / a)) {
            posx = new_x;
        }
        // Then move in y
        double y_sign = Math.signum(dir_y);
        if (!world.terrainBlocks(posx + width / a, new_y + y_sign * height / a)
                && !world.terrainBlocks(posx - width / a, new_y + y_sign * height / a)) {
            posy = new_y;
        }

        if (posx == p_x && posy == p_y) {
            this.isMove = false;
        } else {
            this.isMove = true;
        }
    }

    /**
     * 繪製名稱和status,名稱居中且距離bar左右各5個像素
     * 
     */
    protected void drawNameBar(Graphics g) {
        // 繪製狀態指示圖示（頭上方顯示角色名字和健康值）
        // Color redcolor = new Color(0xcc, 0x0E, 0x0E);

        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f); // Red, transp
        if (this.HP == 1) {
            BAR = new Color(67, 133, 190); // Red, transp
        }

        int text_width = g.getFont().getWidth(this.name);
        float bar_width = text_width + 10;

        int text_x = (int) (posx - text_width / 2);
        int text_y = (int) (posy - Main.NAME_BAR_HEIGHT - this.height / 2);
        float bar_x = text_x - 5;
        float bar_y = text_y;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, Main.NAME_BAR_HEIGHT);

        int hp_bar_width = (int) (bar_width * this.getPercentage());
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, Main.NAME_BAR_HEIGHT);

        g.setColor(Color.white);
        g.drawString(this.name, text_x, text_y);
    }
}
