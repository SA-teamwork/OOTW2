package role;
/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Sample Solution
 * Author: Matt Giuca <mgiuca>
 */

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import item.Item;
import item.UnitsData;
import item.UnitsData.Record;
import role.unit.Player;
import role.unit.Villager.Bedivere;
import role.unit.Villager.Guinevere;
import role.unit.Villager.Lancelot;
import role.unit.Villager.VillagersTemplete;
import role.unit.monster.AggressiveMonster;
import role.unit.monster.PassiveMonster;

/**
 * Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World implements Cloneable{
    /**
     * 被動攻擊者復活等待時間
     * 等待復活時間小於0表示角色不可以復活，一旦hp<=0，則直接滅絕。 in mills
     * 等待復活時間大於0表示角色可以復活，一旦hp<=0，則角色進入復活等待時間，等待時間到後角色進行原地復活。
     */
    private static final long PassiveMonsterResurgence = -1L;
    /**
     * 主攻擊者復活等待時間,為-l表示不可以復活，一旦hp不大於0，則直接滅絕。 in mills
     */
    private static final long AggressiveMonsterResurgence = -1L;
    /**
     * 玩家復活等待時間,一旦hp不大於0，玩家可以。 in mills
     */
    private static final long PlayerResurgence = 3000L;
    public static final int PLAYER_START_X = 756, PLAYER_START_Y = 684;
    private static final int STATUS_PANEL_X = PLAYER_START_X - Main.SCREEN_WIDTH / 2;
    private static final int STATUS_PANEL_Y = PLAYER_START_Y + Main.SCREEN_HEIGHT / 2 - Main.STATUS_PANEL_HEIGHT;

    private Player player;
    private List<VillagersTemplete> villagers;
    private Item[] items;
    private PassiveMonster[] passiveMs;
    private List<AggressiveMonster> aggressiveMs;
    private StatusPanel statusP;
    private TiledMap map;
    private Camera camera;

    /**
     * Map width, in pixels.
     */
    private int getMapWidth() {
        return map.getWidth() * getTileWidth();
    }

    /**
     * Map height, in pixels.
     */
    private int getMapHeight() {
        return map.getHeight() * getTileHeight();
    }

    /**
     * Tile width, in pixels.
     */
    private int getTileWidth() {
        return map.getTileWidth();
    }

    /**
     * Tile height, in pixels.
     */
    private int getTileHeight() {
        return map.getTileHeight();
    }

    private World(World w) {
        this.player = new Player(w.player);

        this.villagers = new ArrayList<>();
        for (VillagersTemplete v : w.villagers) {
            VillagersTemplete vc = v.clone();

            this.villagers.add(vc);
            this.player.addChatObserver(vc);
        }

        this.aggressiveMs = new ArrayList<>();
        for (AggressiveMonster am : w.aggressiveMs) {
            AggressiveMonster amc = new AggressiveMonster(am);

            this.aggressiveMs.add(amc);
            amc.addAttackObserver(this.player);
            this.player.addAttackObserver(amc);
            this.player.addMoveObserver(amc);
        }

        this.items = new Item[w.items.length];
        for (int i = 0; i < w.items.length; i++) {
            this.items[i] = new Item(w.items[i], this.player);
            this.player.addMoveObserver(this.items[i]);
        }

        this.passiveMs = new PassiveMonster[w.passiveMs.length];
        for (int i = 0; i < w.passiveMs.length; i++) {
            this.passiveMs[i] = new PassiveMonster(w.passiveMs[i]);
            this.player.addAttackObserver(this.passiveMs[i]);
        }

        this.statusP = new StatusPanel(w.statusP, player);
        this.player.addMoveObserver(statusP);

        try {
            this.map = new TiledMap(Main.ASSETS_MAP_PATH + "/map.tmx", Main.ASSETS_MAP_PATH);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.camera = new Camera(w.camera, player);
    }

    public World() {
        UnitsData unitData = UnitsData.getInstance("assets/units.dat");
        try {
            player = new Player(Main.ASSETS_PATH + "/units/player/", 72, 72, PLAYER_START_X, PLAYER_START_Y, 100, 26, 600, PlayerResurgence);
            map = new TiledMap(Main.ASSETS_MAP_PATH + "/map.tmx", Main.ASSETS_MAP_PATH);
            camera = new Camera(player, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT - Main.STATUS_PANEL_HEIGHT);

            initVillager(unitData);
            initPassiveMonster(unitData);
            initAggressiveMonster(unitData);
            initItem();
            initStatePanel();

        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    private void initStatePanel() {
        statusP = new StatusPanel(Main.ASSETS_PATH + "/panel.png", STATUS_PANEL_X, STATUS_PANEL_Y,
                Main.SCREEN_WIDTH,
                Main.STATUS_PANEL_HEIGHT, player);
        player.addMoveObserver(statusP);
    }

    private void initItem() throws SlickException {
        int numOfApple = 12;
        items = new Item[4 + numOfApple];

        items[0] = new Item(Main.ASSETS_PATH + "/items/amulet.png", 965, 3563, "amulet", this.player);
        items[1] = new Item(Main.ASSETS_PATH + "/items/sword.png", 546, 6707, "sword", this.player);
        items[2] = new Item(Main.ASSETS_PATH + "/items/tome.png", 4791, 1253, "tome", this.player);
        items[3] = new Item(Main.ASSETS_PATH + "/items/elixir.png", 1976, 402, "elixir", this.player);

        int x = getMapWidth();
        int y = getMapHeight();
        double rx;
        double ry;
        for (int i = 1; i <= numOfApple; i++) {
            rx = Math.random() * x;
            ry = Math.random() * y;
            while (this.terrainBlocks(rx, ry)) {
                rx = Math.random() * x;
                ry = Math.random() * y;
            }

            items[3 + i] = new Item(Main.ASSETS_PATH + "/items/apple.png", rx, ry, "apple",true, this.player);
            player.addMoveObserver(items[3 + i]);
        }

        player.addMoveObserver(items[0]);
        player.addMoveObserver(items[1]);
        player.addMoveObserver(items[2]);
        player.addMoveObserver(items[3]);
    }

    /**
     * 初始化村民
     */
    private void initVillager(UnitsData unitData) throws SlickException {
        villagers = new ArrayList<>();
        List<Record> records = unitData.getRecords("PrinceAldric");
        if (records != null) {
            for (Record rec : records) {
                VillagersTemplete vl = new Bedivere(Main.ASSETS_PATH + "/units/prince.png",
                        rec.getPosx(), rec.getPosy(),
                        1, 0, 0,
                        "Bedivere"
                        // rec.getName()
                );
                player.addChatObserver(vl);
                villagers.add(vl);
            }
        }
        records = unitData.getRecords("Elvira");
        if (records != null) {
            for (Record rec : records) {
                VillagersTemplete vl = new Guinevere(Main.ASSETS_PATH + "/units/shaman.png",
                        rec.getPosx(), rec.getPosy(),
                        1, 0, 0,
                        "Guinevere"
                        // rec.getName()
                );
                player.addChatObserver(vl);
                villagers.add(vl);
            }
        }
        records = unitData.getRecords("Garth");
        if (records != null) {
            for (Record rec : records) {
                VillagersTemplete vl = new Lancelot(Main.ASSETS_PATH + "/units/peasant.png",
                        rec.getPosx(), rec.getPosy(),
                        1, 0, 0,
                        "Lancelot"
                        // rec.getName()
                );
                player.addChatObserver(vl);
                villagers.add(vl);
            }
        }
    }

    /**
     * 初始化主動怪獸
     */
    private void initAggressiveMonster(UnitsData unitData) throws SlickException {
        aggressiveMs = new ArrayList<>();
        List<Record> records = unitData.getRecords("Zombie");
        if (records != null) {
            for (Record rec : records) {
                AggressiveMonster am = new AggressiveMonster(Main.ASSETS_PATH + "/units/zombie.png", rec.getPosx(),
                        rec.getPosy(), 60, 10, 800, AggressiveMonsterResurgence, rec.getName());
                am.addAttackObserver(player);
                player.addAttackObserver(am);
                player.addMoveObserver(am);
                aggressiveMs.add(am);
            }
        }
        List<Record> bandits = unitData.getRecords("Bandit");
        if (bandits != null) {
            for (Record rec : bandits) {
                AggressiveMonster am = new AggressiveMonster(Main.ASSETS_PATH + "/units/bandit.png", rec.getPosx(),
                        rec.getPosy(), 40, 8, 200, AggressiveMonsterResurgence, rec.getName());
                am.addAttackObserver(player);
                player.addAttackObserver(am);
                player.addMoveObserver(am);
                aggressiveMs.add(am);
            }
        }
        List<Record> skeletons = unitData.getRecords("Skeleton");
        if (skeletons != null) {
            for (Record rec : skeletons) {
                AggressiveMonster am = new AggressiveMonster(Main.ASSETS_PATH + "/units/skeleton.png", rec.getPosx(),
                        rec.getPosy(), 100, 16, 500, AggressiveMonsterResurgence, rec.getName());
                am.addAttackObserver(player);
                player.addAttackObserver(am);
                player.addMoveObserver(am);
                aggressiveMs.add(am);
            }
        }
        List<Record> draelics = unitData.getRecords("Draelic");
        if (draelics != null) {
            for (Record rec : draelics) {
                AggressiveMonster am = new AggressiveMonster(Main.ASSETS_PATH + "/units/necromancer.png", rec.getPosx(),
                        rec.getPosy(), 140, 30, 400, AggressiveMonsterResurgence, rec.getName());
                am.addAttackObserver(player);
                player.addAttackObserver(am);
                player.addMoveObserver(am);
                aggressiveMs.add(am);
            }
        }
    }

    /**
     * 初始化被動怪獸
     */
    private void initPassiveMonster(UnitsData unitData) throws SlickException {
        List<Record> giants = unitData.getRecords("GiantBat");
        if (giants != null) {
            passiveMs = new PassiveMonster[giants.size()];
            for (int i = 0; i < giants.size(); i++) {
                Record rec = giants.get(i);
                // passiveMs[i] = new PassiveMonster(Main.ASSETS_PATH + "/units/dreadbat.png",
                // rec.getPosx(), rec.getPosy(),
                // 40, 0, 0, PassiveMonsterResurgence, rec.getName());
                passiveMs[i] = new PassiveMonster(Main.ASSETS_PATH + "/units/slime/", 72, 72, rec.getPosx(),
                        rec.getPosy(),
                        40, 0, 0, PassiveMonsterResurgence, "Slime");

                player.addAttackObserver(passiveMs[i]);
            }
        }
    }

    /**
     * Update the game state for a frame.
     *
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(int dir_x, int dir_y, int delta)
            throws SlickException {
//        this.t = System.currentTimeMillis();
        player.update(this, dir_x, dir_y, delta);
        for (PassiveMonster pm : passiveMs)
            pm.update(this, dir_x, dir_y, delta, player);
        for (AggressiveMonster am : aggressiveMs)
            am.update(this, dir_x, dir_y, delta, player);
        for (VillagersTemplete vl : villagers)
            vl.update(this, dir_x, dir_y, delta);
        camera.update();
    }

    /**
     * Render the entire screen, so it reflects the current game state.
     *
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
            throws SlickException {
        // Render the relevant section of the map
        int x = -(camera.getMinX() % getTileWidth());
        int y = -(camera.getMinY() % getTileHeight());
        int sx = camera.getMinX() / getTileWidth();
        int sy = camera.getMinY() / getTileHeight();
        int w = (camera.getMaxX() / getTileWidth()) - (camera.getMinX() / getTileWidth()) + 1;
        int h = (camera.getMaxY() / getTileHeight()) - (camera.getMinY() / getTileHeight()) + 1;
        map.render(x, y, sx, sy, w, h);
        // map.render(0, 0, sx, sy, w, h);
        // map.render(100, 100, 0, 0, 5, 5);

        // Translate the Graphics object
        g.translate(-camera.getMinX(), -camera.getMinY());

        // Render the player
        player.render(g);
        for (VillagersTemplete villager : villagers)
            villager.render(g);
        for (Item item : items)
            item.render(g);
        for (PassiveMonster pm : passiveMs)
            pm.render(g);
        for (AggressiveMonster am : aggressiveMs)
            am.render(g);
        Image backGround = new Image(Main.ASSETS_PATH + "/fag_apha.png");
        g.drawImage(backGround, camera.getMinX(), camera.getMinY());
        statusP.render(g);
    }

    /**
     * Determines whether a particular map coordinate blocks movement.
     *
     * @param x Map x coordinate (in pixels).
     * @param y Map y coordinate (in pixels).
     * @return true if the coordinate blocks movement.
     */
    public boolean terrainBlocks(double x, double y) {
        // Check we are within the bounds of the map
        if (x < 0 || y < 0 || x > getMapWidth() || y > getMapHeight()) {
            return true;
        }

        // Check the tile properties
        int tile_x = (int) x / getTileWidth();
        int tile_y = (int) y / getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        int tileid1 = map.getTileId(tile_x, tile_y, 1);
        String block = map.getTileProperty(tileid, "block", "0");
        String block1 = map.getTileProperty(tileid1, "block", "0");
        // map.getLayerProperty(tileid1, block, block1)
        if (map.getTileId(tile_x, tile_y, 1) != 0) {
            return !block1.equals("0");
        } else {
            return !block.equals("0");
        }
    }

    /**
     * 開始會話
     */
    public void openChat(int delta) {
        player.openChat(delta);
    }

    /**
     * player開始攻擊
     *
     * @param dir_x player移動的x方向
     * @param dir_y player移動的y方向
     * @param delta delta Time passed since last frame (milliseconds).
     */
    public void operatePlayerAttack(int dir_x, int dir_y, int delta) {
        player.attack(dir_x, dir_y, delta);
    }


    @Override
    public World clone() {
        try {
            super.clone();
            return new World(this);
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
