package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public class CenterDown implements PosStrategy {
    @Override
    public Point computePos(UnicodeFont f, Point p, String text) {
        float x, y;
        x = p.getX() - (float) f.getWidth(text) / 2;
        y = p.getY() - f.getHeight(text);
        return new Point(x, y);
    }
}
