package fontMgr;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Point;

public class CenterCenter implements PosStrategy {
    @Override
    public Point computePos(UnicodeFont f, Point p, String text) {
        float x, y;
        x = p.getX() - (float) f.getWidth(text) / 2;
        y = p.getY() - (float) f.getHeight(text) / 2;
        return new Point(x, y);
    }
}
