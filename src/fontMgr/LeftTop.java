package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public class LeftTop implements PosStrategy {
    @Override
    public Point computePos(UnicodeFont f, Point p, String text) {
        return p;
    }
}
