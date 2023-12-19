package role;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */
public class RPG extends BasicGameState {
    private World world;
    private Font f;
    private ArrayList<Integer> pws = new ArrayList<Integer>();

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.world = new World();
        world.update(0, 0, 0); // 因為初始化完成的第一禎畫面會跑掉，故出此下強制更新畫面。
        try {
            UnicodeFont f = new UnicodeFont(Main.ASSETS_PATH + "/Silver.ttf", 24, false,
                    false);
            f.getEffects().add(new ColorEffect());
            f.addAsciiGlyphs();
            f.addGlyphs(0x4E00, 0x9FFF);
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
        world.render(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
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
