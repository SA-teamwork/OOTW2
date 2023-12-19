import item.DistUtilsTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.newdawn.slick.SlickException;
import role.CameraTest;

public class TestAll extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new DistUtilsTest("tearDown") {
            protected void runTest() {
                testDist();
            }
        });
        suite.addTest(new CameraTest("testUpdatePos") {
            protected void runTest() {
                try {
                    testUpdatePos();
                } catch (SlickException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return suite;
    }
}
