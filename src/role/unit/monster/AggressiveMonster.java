package role.unit.monster;

import observer.MonsterAttackObservable;
import observer.MonsterAttackObserver;
import observer.PlayerAttackObserver;
import observer.PlayerMoveObserver;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import role.DistUtils;
import role.World;
import role.unit.Player;
import role.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AggressiveMonster extends Unit
        implements PlayerMoveObserver, MonsterAttackObservable, PlayerAttackObserver {

    private static double SPEED = 0.25;
    private int dir_y = 0;
    private int dir_x = 0;
    /**
     * 戰鬥狀態
     */
    private boolean isCombat = false;
    /**
     * 移動狀態
     */
    private boolean isMove = false;
    private List<MonsterAttackObserver> attackObservers = new ArrayList<>();
    /**
     * 在(0-attack) Random dmg
     */
    private Random rd;

    public AggressiveMonster(AggressiveMonster a) {
        super(a);
        this.dir_y = a.dir_y;
        this.dir_x = a.dir_x;
        this.isCombat = a.isCombat;
        this.isMove = a.isMove;
        this.rd = new Random();
    }

    public AggressiveMonster(String image_path, double posx, double posy, int MAX_HP, int attack, int CoolDown,
                             long resurgence, String name) throws SlickException {
        super(image_path, posx, posy, MAX_HP, attack, CoolDown, resurgence, name);
        rd = new Random();
    }

    public AggressiveMonster(String image_path, int tw, int th, double posx, double posy, int MAX_HP, int attack,
                             int CoolDown,
                             long resurgence, String name) throws SlickException {
        super(image_path, tw, th, posx, posy, MAX_HP, attack, CoolDown, resurgence, name, true);
        rd = new Random();
    }

    public boolean isCombat() {
        return isCombat;
    }

    public void setCombat(boolean isCombat) {
        this.isCombat = isCombat;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean isMove) {
        this.isMove = isMove;
    }

    @Override
    public void action(Player player, double posx, double posy, double delta) {
        if (this.isActive() && player.isActive()) {
            double dist = DistUtils.dist(this.getPosx(), this.getPosy(), posx, posy);
            if (dist > 150) {
                this.setMove(false);
                this.setCombat(false);
            } else if (dist <= 150 && dist > 50) {
                this.setMove(true);
                this.setCombat(false);
            } else if (dist <= 50) {
                this.setMove(false);
                this.setCombat(true);
            }
        } else {
            this.setCombat(false);
            this.setMove(false);
        }
    }

    /**
     * 發動攻擊
     */
    public void attack(double delta) {
        // out CD?
        if (this.getCoolDownTime() <= 0) {
            // ramdom atk dmg
            int attValue = rd.nextInt(this.getAttack());
            // notify player
            this.notifyAttackObservers(dir_x, dir_y, this.getPosx(), this.getPosy(), delta, attValue);
            // in CD
            this.startCoolDownTime();
        }
    }

    /**
     * 被攻擊
     */
    @Override
    public void handleAttack(int dir_x, int dir_y, double posx, double posy, double delta, int attackValue) {
        if (this.isActive()) {
            double dist = DistUtils.dist(this.getPosx(), this.getPosy(), posx, posy);
            if (dist <= this.getWidth() * 1.2) {
                this.cutHp(attackValue);
            }
        }
    }

    public void move(World world, Player player, double delta) {
        calculateAttackMonsterCoordinate(world, SPEED, delta, player.getPosx(), player.getPosy(), false);
    }

    public void update(World world, int dir_x, int dir_y, int delta, Player player) {
        super.update(world, dir_x, dir_y, delta);
        if (this.isActive() && player.isActive()) {
            // can Atk?
            if (this.isCombat())
                attack(delta);
            // can Move?
            if (this.isMove())
                move(world, player, delta);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        if (this.isActive()) {
            this.drawNameBar(g);
        }
    }

    @Override
    public void comeBack() {
        super.comeBack();
        this.setCombat(false);
    }

    @Override
    public void addAttackObserver(MonsterAttackObserver observer) {
        attackObservers.add(observer);
    }

    @Override
    public void delAttackObserver(MonsterAttackObserver observer) {
        attackObservers.remove(observer);
    }

    @Override
    public void notifyAttackObservers(int dir_x, int dir_y, double posx, double posy, double delta, int attackValue) {
        for (MonsterAttackObserver observer : attackObservers) {
            observer.handleAttack(dir_x, dir_y, posx, posy, delta, attackValue);
        }
    }
}
