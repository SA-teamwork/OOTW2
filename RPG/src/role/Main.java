package role;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {
    // public static final String ASSETS_PATH = "./assets";
    // public static final String ASSETS_MAP_PATH = "./assets/map";
    // public static final String ASSETS_PATH =
    // "C:\\Users\\ah_flowey\\Documents\\OOTW1\\RPG\\assets";
    // public static final String ASSETS_MAP_PATH =
    // "C:\\Users\\ah_flowey\\Documents\\OOTW1\\RPG\\assets\\map";
    public static final String ASSETS_PATH = "C:\\Users\\selab\\IdeaProjects\\OOTW2\\RPG\\assets";
    public static final String ASSETS_MAP_PATH = "C:\\Users\\selab\\IdeaProjects\\OOTW2\\RPG\\assets\\map";

    /** Screen width, in pixels. */
    public static final int SCREEN_WIDTH = 800;
    /** Screen height, in pixels. */
    public static final int SCREEN_HEIGHT = 675;
    /** panel height, in pixels. */
    public static final int STATUS_PANEL_HEIGHT = 75;
    /** name bar height, in pixels. */
    public static final int NAME_BAR_HEIGHT = 20;
    /** The world of our game */

    static {
        // File PATH = new File("./lwjgl-2.9.3./lwjgl-2.9.3./native/windows");

        File PATH = new File("C:\\Users\\selab\\Documents\\OOTW1\\OOTW1\\lwjgl-2.9.3\\lwjgl-2.9.3\\n"
                + "ative\\windows");
        // File PATH = new
        // File("C:\\Users\\ah_flowey\\Documents\\lwjgl-2.9.3\\lwjgl-2.9.3\\n" + //
        // "ative\\windows");
        System.setProperty("org.lwjgl.librarypath", PATH.getAbsolutePath()); // This is the property not the regular
                                                                             // Djava.library.path
        System.setProperty("net.java.games.input.librarypath", PATH.getAbsolutePath());
        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("Test!!"));
        app.setShowFPS(true);
        app.setTargetFrameRate(60);
        // app.setFullscreen(false);
        // app.setDisplayMode((int) (800 * 1.2), (int) (600 * 1.2), false);
        // app.setDisplayMode(1920, 1000, false);
        app.setDisplayMode(800, 675, false);
        // app.
        // app.setFullscreen(true);
        app.start();
    }

    public Main(String name) {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer arg0) throws SlickException {
        this.addState(new StartPanel());
        this.addState(new RPG());
        this.addState(new terminatePanel());
    }

}
