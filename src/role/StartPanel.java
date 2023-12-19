package role;

import fontMgr.FontMgr;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class StartPanel extends BasicGameState {
    private double passTime = 0.0;
    private FontMgr fontMgr;
    private String title, subTitle;
    private Image backGround;
    private int x, y, titleDelta = 0;


    @Override
    public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
        fontMgr = FontMgr.getFontMgr();
        title = "Redemption of the Kingdom";
        subTitle = "Type [Enter] to start GAME";
        backGround = new Image(Main.ASSETS_PATH + "/startPanel.png");
        x = gc.getWidth() / 2;
        y = gc.getHeight() / 4;
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(backGround, 0.0f, 0.0f);
        fontMgr.drawString("Silver_Title", x, y + titleDelta, title, 4);
        fontMgr.drawString("Silver_SubTitle", x, y + titleDelta + 100, subTitle, 4);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        Input input = gc.getInput();
        if (input.isKeyPressed(Input.KEY_ENTER)) {
            input.clearKeyPressedRecord();
            sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }
        passTime += delta / 750.0;
        titleDelta = (int) (Math.sin(this.passTime) * 10);
    }

    @Override
    public int getID() {
        return 0;
    }

}
