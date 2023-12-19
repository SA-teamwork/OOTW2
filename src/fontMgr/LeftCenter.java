package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public class LeftCenter implements PosStrategy {
    @Override
    public Point computePos(UnicodeFont f, Point p, String text) {
        float x, y;
        x = p.getX();
        y = p.getY() - (float) f.getHeight(text) / 2;
        return new Point(x, y);
    }
}
