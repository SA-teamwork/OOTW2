package role.unit;
/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Sample Solution
 * Author: Matt Giuca <mgiuca>
 */

import item.Item;
import observer.*;
import org.newdawn.slick.SlickException;
import role.DistUtils;
import role.World;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * The character which the user plays as.
 */
public class Player extends Unit
        implements PlayerMoveObservable, PlayerAttackObservable, PlayerChatObservable, MonsterAttackObserver {
    // Pixels per millisecond
    private static final double SPEED = 0.5;
    // 存放監聽player移動動作的監聽者。
    private List<PlayerMoveObserver> observers = new ArrayList<>();
    // 存放監聽player攻擊動作的監聽者。
    private List<PlayerAttackObserver> attackObservers = new ArrayList<>();
    // 存放監聽player chat動作的監聽者。
    private List<PlayerChatObserver> chatObservers = new ArrayList<>();
    private LinkedHashMap<String, Item> itemsmap = new LinkedHashMap<>();
    // whether get the elixir
    private boolean elixir;
    /**
     * Random atk
     */
    private Random rd;

    public Player(Player p) {
        super(p);
        // this.observers = new ArrayList<PlayerMoveObserver>(p.observers);
        // this.attackObservers = new
        // ArrayList<PlayerAttackObserver>(p.attackObservers);
        // this.chatObservers = new ArrayList<PlayerChatObserver>(p.chatObservers);
        this.itemsmap = new LinkedHashMap<>(p.itemsmap);

        this.elixir = p.elixir;
        this.rd = new Random();
    }

    public Player(String image_path, int tw, int th, double posx, double posy, int MAX_HP, int attack, int CoolDown,
                  long resurgence)
            throws SlickException {
        super(image_path, tw, th, posx, posy, MAX_HP, attack, CoolDown, resurgence, null, true);
        System.out.println("Player");
        this.elixir = false;
        rd = new Random();
    }

    public boolean hasElixir() {
        return elixir;
    }

    public void setElixir(boolean elixir) {
        this.elixir = elixir;
    }

    @Override
    public void addMoveObserver(PlayerMoveObserver observer) {
        observers.add(observer);
    }

    @Override
    public void deleteMoveObserver(PlayerMoveObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(double posx, double posy, double delta) {
        for (PlayerMoveObserver mob : observers) {
            mob.action(this, posx, posy, delta);
        }
    }

    public LinkedHashMap<String, Item> getItems() {
        return itemsmap;
    }

    public Item getItem(String itemName) {
        return itemsmap.get(itemName);
    }

    public void putItem(Item item) {
        synchronized (this) {
            itemsmap.put(item.getName(), item);

        }

    }

    public void removeItem(String itemname) {
        synchronized (this) {
            if (itemsmap.containsKey(itemname)) {
                itemsmap.remove(itemname);
            }
        }
    }

    /**
     * player produce a attack
     *
     * @param dir_x Player移動的x方向
     * @param dir_y Player移動的y方向
     * @param delta delta Time passed since last frame (milliseconds).
     */
    public void attack(int dir_x, int dir_y, int delta) {
        // Can ATK?
        if (this.getCoolDownTime() <= 0) {
            // Random DMG
            int attValue = rd.nextInt(this.getAttack());
            this.notifyAttackObservers(dir_x, dir_y, this.getPosx(), this.getPosy(), delta, attValue);
            // inCD
            this.startCoolDownTime();
        }
    }

    public void openChat(int delta) {
        this.notifyChatObservers(this.getPosx(), this.getPosy(), delta);
    }

    @Override
    public void handleAttack(int dir_x, int dir_y, double posx, double posy, double delta, int attackValue) {
        if (this.isActive()) {
            // in range?
            double dist = DistUtils.dist(this.getPosx(), this.getPosy(), posx, posy);
            if (dist <= 50) {
                this.cutHp(attackValue);
            }
        }
    }

    /**
     * Move the player in a given direction.
     * Prevents the player from moving outside the map space, and also updates
     * the direction the player is facing.
     *
     * @param world The world the player is on (to check blocking).
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void move(World world, double dir_x, double dir_y, double delta) {
        // calculate new coordinate
        if (Math.abs(dir_x) + Math.abs(dir_y) == 2) {
            dir_x /= Math.sqrt(2);
            dir_y /= Math.sqrt(2);
        }
        calculateNewCoordinate(world, dir_x, dir_y, SPEED, delta);
        notifyObservers(this.getPosx(), this.getPosy(), delta);
    }

    @Override
    public void update(World world, int dir_x, int dir_y, int delta) {
        super.update(world, dir_x, dir_y, delta);
        if (this.isActive()) {
            move(world, dir_x, dir_y, delta);
        }
    }

    @Override
    public void comeBack() {
        super.comeBack();
        this.setPosx(World.PLAYER_START_X);
        this.setPosy(World.PLAYER_START_Y);
    }

    @Override
    public void addAttackObserver(PlayerAttackObserver observer) {
        attackObservers.add(observer);
    }

    @Override
    public void delAttackObserver(PlayerAttackObserver observer) {
        attackObservers.remove(observer);
    }

    @Override
    public void notifyAttackObservers(int dir_x, int dir_y, double posx, double posy, double delta, int attackValue) {
        for (PlayerAttackObserver observer : attackObservers) {
            observer.handleAttack(dir_x, dir_y, posx, posy, delta, attackValue);
        }
    }

    @Override
    public void addChatObserver(PlayerChatObserver observer) {
        chatObservers.add(observer);
    }

    @Override
    public void deleteChatObserver(PlayerChatObserver observer) {
        chatObservers.remove(observer);
    }

    @Override
    public void notifyChatObservers(double posx, double posy, double delta) {
        for (PlayerChatObserver observer : chatObservers) {
            observer.chatAction(this, posx, posy, delta);
        }
    }

}
