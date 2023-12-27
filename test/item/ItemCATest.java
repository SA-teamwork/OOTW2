package item;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import role.Main;
import role.unit.Player;

import java.io.File;

public class ItemCATest extends TestCase {

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

//        Case1: Null Pikcer Error
        try {
            i.action(null, 20.1, 0, 0);
            Assert.assertEquals("Didn't throw NullPointerException", true);
        } catch (NullPointerException ignored) {

        }
//        Case2: Do nothing
        i.action(p, 20.1, 0, 0);
        Assert.assertTrue("不應該pick", i.isVisible());
//        Case3: Pick!!
        i.action(p, 20, 0, 0);
        Assert.assertTrue("應該pick", i.isVisible());

    }
}