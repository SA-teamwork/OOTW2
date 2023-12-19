package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public class RightTop implements PosStrategy {
    @Override
    public Point computePos(UnicodeFont f, Point p, String text) {
        float x, y;
        x = p.getX() - f.getWidth(text);
        y = p.getY();
        return new Point(x, y);
    }
}
