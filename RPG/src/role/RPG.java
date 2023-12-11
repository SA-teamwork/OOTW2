package role;
/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 */

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */
public class RPG extends BasicGameState {
    private World world;
    // private World worldClone;
    private Font f;
    private ArrayList<Integer> pws = new ArrayList<Integer>();

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.world = new World();
        world.update(0, 0, 0); // 因為初始化完成的第一禎畫面會跑掉，故出此下強制更新畫面。
        try {
            UnicodeFont f = new UnicodeFont(Main.ASSETS_PATH + "/Silver.ttf", 24, false,
                    false);
            // UnicodeFont f = new UnicodeFont(RPG.ASSETS_PATH + "/NotoSansTC-Light.ttf",
            // 12, true, false);
            f.getEffects().add(new ColorEffect());
            f.addAsciiGlyphs();
            f.addGlyphs(0x4E00, 0x9FFF);
            // f.addGlyphs(0x2e80, 0x2fdf); // CKJ radicals supplement + Kangxi
            // f.addGlyphs(0x3040, 0x30ff); // Hiragana + katakana
            // f.addGlyphs(0x3300, 0x33ff); // CJK Unified Ideographs Extension A
            // f.addGlyphs(0xf900, 0xfaff); // CJK compatibility Ideographs;
            // f.addGlyphs("請尋找長生不老藥來治癒國王");
            f.loadGlyphs();
            this.f = f;
        } catch (SlickException e) {
            System.out.println("Err!!");
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.setFont(this.f);
        // this.f.drawString(10, 50, String.valueOf(this.t));
        // g.drawString("test", 50, 50);
        world.render(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        // Boolean cl = false;

        // Update the player's movement direction based on keyboard presses.
        int dir_x = 0;
        int dir_y = 0;

        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            input.clearKeyPressedRecord();
            sbg.enterState(2);
        }

        if (input.isKeyDown(Input.KEY_DOWN))
            dir_y += 1;
        if (input.isKeyDown(Input.KEY_UP))
            dir_y -= 1;
        if (input.isKeyDown(Input.KEY_LEFT))
            dir_x -= 1;
        if (input.isKeyDown(Input.KEY_RIGHT))
            dir_x += 1;

        // talk with villager
        if (input.isKeyPressed(Input.KEY_T)) {
            world.openChat(delta);
        }

        // player attack villagers
        if (input.isKeyDown(Input.KEY_A)) {
            world.operatePlayerAttack(dir_x, dir_y, delta);
        }

        // Let World.update decide what to do with this data.
        world.update(dir_x, dir_y, delta);
        // System.out.println("RPG.update done");
    }

    @Override
    public int getID() {
        return 1;
    }

    public void setMemento(Memento m, int i) throws IllegalAccessException, CloneNotSupportedException {
        this.world = m.getWorld(this.pws.get(i));
        // System.out.println(String.valueOf(this.world.t));
        // System.out.println("restore");
        // this.world = s;
    }

    public Memento getMemento() throws CloneNotSupportedException {
        Memento m = new Memento();
        this.pws.add(m.setWorld(world));
        return m;
    }

    public void removeMemento(int i) {
        this.pws.remove(i);
    }
}
