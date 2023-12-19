package fontMgr;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Point;
import role.Main;

import java.util.HashMap;

public class FontMgr {
    private static FontMgr fontMgr = null;
    private HashMap<String, UnicodeFont> fontMap = new HashMap<>();
    private PosStrategy ps;


    private FontMgr() {
        addFont("Silver_Title", Main.ASSETS_PATH + "/Silver.ttf", 86, true, false);
        addFont("Silver_SubTitle", Main.ASSETS_PATH + "/Silver.ttf", 37, false, false);
        addFont("Silver_para", Main.ASSETS_PATH + "/Silver.ttf", 24, false, false);
    }

    public static FontMgr getFontMgr() {
        if (fontMgr == null) {
            fontMgr = new FontMgr();
        }
        return fontMgr;
    }

    public void addFont(String fontKey, String ttfFileRef, int size, boolean bold, boolean italic) {
        try {
            UnicodeFont font = new UnicodeFont(ttfFileRef, size, bold, italic);
            font.getEffects().add(new ColorEffect());
            font.addAsciiGlyphs();
            font.loadGlyphs();

            fontMap.put(fontKey, font);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFont(String fontKey, String ttfFileRef, int size, boolean bold, boolean italic, ColorEffect ce) {
        try {
            UnicodeFont font = new UnicodeFont(ttfFileRef, size, bold, italic);
            font.getEffects().add(ce);
            font.addAsciiGlyphs();
            font.loadGlyphs();

            fontMap.put(fontKey, font);
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param posID \ L C R
     *              T 0 1 2
     *              C 3 4 5
     *              D 6 7 8
     */
    public void selectPos(int posID) {
        switch (posID) {
            case 0 -> this.ps = new LeftTop();
            case 1 -> this.ps = new CenterTop();
            case 2 -> this.ps = new RightTop();
            case 3 -> this.ps = new LeftCenter();
            case 4 -> this.ps = new CenterCenter();
            case 5 -> this.ps = new RightCenter();
            case 6 -> this.ps = new LeftDown();
            case 7 -> this.ps = new CenterDown();
            case 8 -> this.ps = new RightDown();
            default -> this.ps = new LeftTop();
        }
    }

    public void drawString(String fontKey, float x, float y, String text, int posID) {
        UnicodeFont font = fontMap.get(fontKey);
        selectPos(posID);
        Point p = new Point(x, y);
        p = ps.computePos(font, p, text);
        font.drawString(p.getX(), p.getY(), text);
    }

}
