package item;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import role.Main;
import role.unit.Player;

import java.io.File;

public class ItemEETest extends TestCase {

    static {
        File PATH = new File("lwjgl-2.9.3/lwjgl-2.9.3/native/windows");
        System.setProperty("org.lwjgl.librarypath", PATH.getAbsolutePath());
        System.setProperty("net.java.games.input.librarypath", PATH.getAbsolutePath());
        System.setProperty("net.java.games.input.useDefaultPlugin", "false");
    }

    private Item i;
    private Player p;

    public void setUp() throws Exception {
        super.setUp();
        Display.setDisplayMode(new DisplayMode(0, 0));
        Display.create();
//        TestUtils utilss = new TestUtils();
//        utils.start();
        p = new Player(Main.ASSETS_PATH + "/units/player/", 72, 72, 0, 0, 100, 26, 600, -1);
        i = new Item(Main.ASSETS_PATH + "/items/amulet.png", 0, 0, "amulet", p);
        p.addMoveObserver(i);
    }

    public void tearDown() throws Exception {
        p = null;
        i = null;
    }

    public void testAction() {
//        init test
        Assert.assertTrue("itemIsUnVisible", i.isVisible());

//        EE Test: out doman
        i.action(p, 100, 100, 0); // > 20
        Assert.assertTrue("itemIsUnVisible", i.isVisible());

//        EE Test: in doman
        i.action(p, 0, 0, 0); // <= 20
        Assert.assertFalse("itemIsVisible", i.isVisible());


    }
}