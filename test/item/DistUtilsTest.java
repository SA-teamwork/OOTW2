package item;

import junit.framework.Assert;
import junit.framework.TestCase;
import role.DistUtils;

public class DistUtilsTest extends TestCase {
    private DistUtils distUtils;

    public DistUtilsTest(String string) {
        super(string);
    }

    public void setUp() throws Exception {
        super.setUp();
        distUtils = new DistUtils();

    }

    public void tearDown() {
        distUtils = null;
    }

    public void testDist() {
        double d = DistUtils.dist(100, 100, 100, 250);
        String msg = String.format("d == %f", d);
        Assert.assertEquals(msg, 150.0, d);
    }
}