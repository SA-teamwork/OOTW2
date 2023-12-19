package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public class RightCenter implements PosStrategy {
    @Override
    public Point computePos(UnicodeFont f, Point p, String text) {
        float x, y;
        x = p.getX() - f.getWidth(text);
        y = p.getY() - (float) f.getHeight(text) / 2;
        return new Point(x, y);
    }
}
