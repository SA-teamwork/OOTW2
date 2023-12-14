package role;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.util.HashMap;

public class FontMgr {
    private HashMap<String, UnicodeFont> fontMap = new HashMap<>();
    private static FontMgr fontMgr = null;

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

    public void drawString_LT(String fontKey, float x, float y, String text) {
        fontMap.get(fontKey).drawString(x, y, text);
    }

    public void drawString_CC(String fontKey, float x, float y, String text) {
        UnicodeFont font = fontMap.get(fontKey);
        x -= (float) font.getWidth(text) / 2;
        y -= (float) font.getHeight(text) / 2;
        font.drawString(x, y, text);
    }

    public void drawString_CT(String fontKey, float x, float y, String text) {
        UnicodeFont font = fontMap.get(fontKey);
        x -= (float) font.getWidth(text) / 2;
//        y -= (float) font.getHeight(text) / 2;
        font.drawString(x, y, text);
    }

    public void drawString_RT(String fontKey, float x, float y, String text) {
        UnicodeFont font = fontMap.get(fontKey);
        x -= font.getWidth(text);
        y -= font.getHeight(text);
        font.drawString(x, y, text);
    }

}
