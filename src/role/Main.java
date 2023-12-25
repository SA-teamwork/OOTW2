package role;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;

public class Main extends StateBasedGame {
    public static final String ASSETS_PATH = "assets";
    public static final String ASSETS_MAP_PATH = "assets/map";

    /**
     * Screen width, in pixels.
     */
    public static final int SCREEN_WIDTH = 800;
    /**
     * Screen height, in pixels.
     */
    public static final int SCREEN_HEIGHT = 675;
    /**
     * panel height, in pixels.
     */
    public static final int STATUS_PANEL_HEIGHT = 75;
    /**
     * name bar height, in pixels.
     */
    public static final int NAME_BAR_HEIGHT = 20;

    static {
        File PATH = new File("lwjgl-2.9.3/lwjgl-2.9.3/native/windows");
        System.setProperty("org.lwjgl.librarypath", PATH.getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", PATH.getAbsolutePath());
        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
    }

    public Main(String name) {
        super(name);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("Test!!"));
        app.setShowFPS(true);
        app.setTargetFrameRate(60);
        app.setDisplayMode(800, 675, false);
        app.start();
    }

    @Override
    public void initStatesList(GameContainer arg0) {
        this.addState(new StartPanel());
        this.addState(new RPG());
        this.addState(new terminatePanel());
    }

    /*
      TODO:
      工廠-
      模板-
      原型-
      策略-
      Composite-
      命令-
      */

}
