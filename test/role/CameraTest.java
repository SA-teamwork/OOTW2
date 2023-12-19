package role;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.newdawn.slick.SlickException;

public class CameraTest extends TestCase {
    private Camera camera;

    public CameraTest(String string) {
        super(string);
    }

    public void setUp() throws Exception {
        super.setUp();
        camera = new Camera(null, 800, 600);
    }

    public void tearDown() {
        camera = null;
    }

    public void testUpdatePos() throws SlickException {
        camera.updatePos(1000, 1000);
        int xPos = camera.getMinX();
        int yPos = camera.getMinY();

        String msg = String.format("xPos=%d, yPos=%d Expected: xPos=%d, yPos=%d", xPos, yPos, 600, 700);
        Assert.assertTrue(msg, xPos == 600 && yPos == 700);
    }
}