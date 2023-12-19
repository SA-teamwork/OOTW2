package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public interface PosStrategy {
    Point computePos(UnicodeFont f, Point p, String text);
}
