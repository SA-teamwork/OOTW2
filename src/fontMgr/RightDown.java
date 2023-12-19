package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public class RightDown implements PosStrategy {
    @Override
    public Point computePos(UnicodeFont f, Point p, String text) {
        float x, y;
        x = p.getX() - f.getWidth(text);
        y = p.getY() - f.getHeight(text);
        return new Point(x, y);
    }
}
