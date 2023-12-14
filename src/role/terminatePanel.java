package role;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class terminatePanel extends BasicGameState {
    private List<Memento> mementoList;
    private int selectMementoIndex = 0;
    private RPG game;
    private final String[] s = {"Type [S] to saving.",
            "Type [UP/DOWN] to select memento.",
            "Type [Z] to restore select memento.",
            "Type [D] to delete select memento.",
            "Type [Enter] back to game."};
    private final String[] mes = {"Saving success!!",
            "Restore success!!",
            "Delete success!!"};

    private int mes_id;
    private double mesLiveTime = 0.0;
    private double minMesLiveTime = 0.5;
    /**
     * {Left, Top, Right, Down}
     */
    private int[] padding = new int[]{100, 25, 100, 25};
    private int margin = 25;

    private FontMgr fontMgr;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        mementoList = new ArrayList<>();
        game = (RPG) sbg.getState(1);
        fontMgr = FontMgr.getFontMgr();
    }

    private void renderLeft() {
        int x = padding[0];
        int y = padding[1];
        int tmp = margin;
        for (String si : s) {
            fontMgr.drawString_LT("Silver_para", x, y, si);
            y += tmp;
        }
    }

    private void renderRigeht(int ss_x) {
        int l = mementoList.size();
        int x = ss_x + padding[0];
        int y = padding[1];
        int tmp = margin;
        int numOfItemInOnePage = 15;

        if (l != 0) {
            int page = selectMementoIndex / numOfItemInOnePage;
            int pMin = page * numOfItemInOnePage;
            int pMax = (page + 1) * numOfItemInOnePage < l ? (page + 1) * numOfItemInOnePage - 1 : l - 1;

            String bar = String.valueOf(pMin + 1) + "/" +
                    String.valueOf(selectMementoIndex + 1) + "/" +
                    String.valueOf(pMax + 1) + "  Total: " +
                    String.valueOf(l);

            fontMgr.drawString_LT("Silver_para", x, y, bar);
            y += tmp;

            for (var m = pMin; m <= pMax; m++) {
                Memento mement = mementoList.get(m);
                if (m == selectMementoIndex) {
                    fontMgr.drawString_LT("Silver_para", x, y, "* " + mement.getT());
                    y += tmp;
                } else {
                    fontMgr.drawString_LT("Silver_para", x, y, mement.getT());
                    y += tmp;
                }
            }
        } else {
            fontMgr.drawString_LT("Silver_para", x, y, "0/0/0  Total: 0");
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        int ss_x = gc.getWidth() / 2;
        int ss_y = gc.getHeight() / 2;

        renderLeft();
        renderRigeht(ss_x);

        if (updateMes(0)) {
            String mesBeShow = mes[mes_id];
            int w = (int) (gc.getWidth() * (minMesLiveTime - mesLiveTime) / minMesLiveTime);
            g.setColor(new Color(0.2f, 0.2f, 0.2f, 1.0f));
            g.fillRect(ss_x - w / 2, gc.getHeight() - 24, w, 24);
            fontMgr.drawString_CT("Silver_para", ss_x, gc.getHeight() - 24, mesBeShow);
        }
    }

    /**
     * 創建一新訊息
     *
     * @param mes_id 訊息類型
     * @param d      已存在時間
     */
    private void initMes(int mes_id, int d) {
        this.mes_id = mes_id;
        this.mesLiveTime += d / 1000.0;
    }

    private boolean updateMes(int d) {
        if (this.mesLiveTime <= 0) {
            return false;
        } else if (this.mesLiveTime >= this.minMesLiveTime) {
            this.mesLiveTime = 0;
            return false;
        } else {
            this.mesLiveTime += d / 1000.0;
            return true;
        }
    }

    private void changeSelectMementoIndex(int i) {
        int l = mementoList.size();
        selectMementoIndex += i;
        if (selectMementoIndex < 0) {
            selectMementoIndex = l - 1;
        }
        if (selectMementoIndex > l - 1) {
            selectMementoIndex = 0;
        }

    }

    private void guiActionUpdate(GameContainer gc, StateBasedGame sbg, int delta)
            throws IllegalAccessException, CloneNotSupportedException {
        Input input = gc.getInput();
        int up_down = 0;
        if (input.isKeyPressed(Input.KEY_S)) {
            initMes(0, delta);
            mementoList.add(game.getMemento());
        } else if (input.isKeyPressed(Input.KEY_Z)) {
            initMes(1, delta);
            game.setMemento(mementoList.get(selectMementoIndex), selectMementoIndex);
        } else if (input.isKeyPressed(Input.KEY_D) && mementoList.size() != 0) {
            initMes(2, delta);
            mementoList.remove(selectMementoIndex);
            game.removeMemento(selectMementoIndex);
        } else if (input.isKeyPressed(Input.KEY_ENTER)) {
            input.clearKeyPressedRecord();
            sbg.enterState(1);
        } else if (input.isKeyPressed(Input.KEY_UP)) {
            up_down = -1;
        } else if (input.isKeyPressed(Input.KEY_DOWN)) {
            up_down = 1;
        }

        changeSelectMementoIndex(up_down);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        if (updateMes(0)) {
            Input input = gc.getInput();
            input.clearKeyPressedRecord();
            updateMes(delta);
        } else {
            try {
                guiActionUpdate(gc, sbg, delta);
            } catch (IllegalAccessException | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getID() {
        return 2;
    }
}
