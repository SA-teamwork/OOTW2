package role;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class StartPanel extends BasicGameState {

    private UnicodeFont tf;
    private UnicodeFont sf;
    private int titleDeltaX = 0;
    private double passTime = 0.0;

    @Override
    public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
        tf = new UnicodeFont(Main.ASSETS_PATH + "/Silver.ttf", 86, true, false);
        sf = new UnicodeFont(Main.ASSETS_PATH + "/Silver.ttf", 37, false, false);

        // tf.getEffects().add(new ColorEffect(new Color(10, 9, 12)));
        tf.getEffects().add(new ColorEffect());
        sf.getEffects().add(new ColorEffect());
        // tf.getEffects().add(new ShadowEffect());
        // sf.getEffects().add(new ShadowEffect());

        tf.addAsciiGlyphs();
        sf.addAsciiGlyphs();
        // tf.addGlyphs(0x4E00, 0x9FFF);
        // sf.addGlyphs(0x4E00, 0x9FFF);
        tf.loadGlyphs();
        sf.loadGlyphs();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        String t = "Redemption of the Kingdom";
        String s = "Type [Enter] to start GAME";
        Image i = new Image(Main.ASSETS_PATH + "/startPanel.png");
        g.drawImage(i, 0.0f, 0.0f);

        int x = gc.getWidth() / 2 - tf.getWidth(t) / 2;
        int y = gc.getHeight() / 2 - tf.getHeight(t) / 2 - 200 + this.titleDeltaX;
        tf.drawString(x, y, t);

        x = gc.getWidth() / 2 - sf.getWidth(s) / 2;
        y = gc.getHeight() / 2 - sf.getHeight(s) / 2 - 100;
        sf.drawString(x, y, s);
        // sf.drawString(10, 20, String.valueOf(this.passTime));
        // sf.drawString(10, 50, String.valueOf(this.titleDeltaX));
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            input.clearKeyPressedRecord();
            sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        this.passTime += delta / 1000.0;
        this.titleDeltaX = (int) (Math.sin(this.passTime * 2) * 5);
    }

    @Override
    public int getID() {
        return 0;
    }

}
