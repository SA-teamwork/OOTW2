package role;

import java.util.LinkedHashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import item.Item;
import observer.PlayerMoveObserver;
import role.unit.Player;

public class StatusPanel implements PlayerMoveObserver {

    private Image panel;
    private float posx;
    private float posy;
    private float width;
    private float height;
    private Player player;
    private FontMgr fontMgr;
    // private String image_path;

    public StatusPanel(StatusPanel sp, Player player) {
        this.panel = sp.panel.copy();
        this.posx = sp.posx;
        this.posy = sp.posy;
        this.width = sp.width;
        this.height = sp.height;
        this.player = player;
        this.fontMgr = sp.fontMgr;
    }

    public StatusPanel(String image_path, float posx, float posy, float width, float height, Player player) {
        super();
        this.posx = posx;
        this.posy = posy;
        this.width = width;
        this.height = height;
        try {
            panel = new Image(image_path);// "/assets/panel.png"
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.player = player;
        this.fontMgr = FontMgr.getFontMgr();
    }

    /**
     * Draw the status panel to the screen at the correct place.
     *
     * @param g The current Graphics context.
     */
    public void render(Graphics g) {
        /*
         * Color bg = new Color(24f, 30f, 29f, 1.0f);
         * g.setColor(bg);
         * g.fillRect(posx, posy, width, height);
         */
        renderPanel(g);

    }

    @Override
    public void action(Player player, double posx, double posy, double delta) {
        drawPanel(posx, posy);

    }

    private void drawPanel(double posx, double posy) {
        this.posx = (int) posx - 400;
        this.posy = (int) posy + 300;
    }

    public void renderPanel(Graphics g) {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f); // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f); // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f); // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f); // Red, transp
        Color BLUE = new Color(0.0f, 0.0f, 0.9f, 0.8f); // BLUE, transp

        // Variables for layout
        String text; // Text to display
        int text_x, text_y; // Coordinates to draw text
        int bar_x, bar_y; // Coordinates to draw rectangles
        int bar_side, bar_height; // Size of rectangle to draw
        int hp_bar_width; // Size of red (HP) rectangle
        int inv_x, inv_y; // Coordinates to draw inventory item

        // Panel background image
        // panel.draw(0, RPG.SCREEN_HEIGHT - RPG.STATUS_PANEL_HEIGTH);
        panel.draw(posx, posy - 5);

        // Display the player's health

        text_x = (int) (posx + 15);
        text_y = (int) (posy + 25);
        g.setColor(LABEL);
        fontMgr.drawString_LT("Silver_para", text_x, text_y, "Health:");
        text = String.valueOf(player.getHP()) + '/' + String.valueOf(player.getMAX_HP());

        bar_x = (int) (posx + 90);
        bar_y = (int) (posy + 20);
        bar_side = 90;
        bar_height = 30;
        hp_bar_width = (int) (bar_side * player.getPercentage());
        text_x = bar_x + (bar_side - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_side, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        fontMgr.drawString_LT("Silver_para", text_x, text_y, text);

        // Display the player's damage and cooldown
        text_x = (int) (posx + 200);
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = String.valueOf(player.getAttack());
        g.setColor(VALUE);
        fontMgr.drawString_LT("Silver_para", text_x, text_y, text);
        text_x += 40;
        g.setColor(LABEL);
        fontMgr.drawString_LT("Silver_para", text_x, text_y, "Rate:");

        bar_x = text_x + 55;
        bar_side -= 40;
        hp_bar_width = (int) (bar_side * player.getCooldownPercentage());
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_side, bar_height);
        g.setColor(BLUE);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);

        text_x = bar_x + 10;
        text = String.valueOf(player.getCoolDown());
        g.setColor(VALUE);
        fontMgr.drawString_LT("Silver_para", text_x, text_y, text);

        // Display the player's inventory
        g.setColor(LABEL);
        fontMgr.drawString_LT("Silver_para", text_x + 50, text_y, "Items:");

        int item_side = 72;
        bar_side = 60;
        bar_x = text_x + 115;
        bar_y = (int) posy + (Main.STATUS_PANEL_HEIGHT - bar_side) / 2;
        int gap = 10;

        for (int i = 0; i < 4; i++) {
            g.setColor(BAR_BG);
            g.fillRect(bar_x + i * (bar_side + gap), bar_y, bar_side, bar_side);
        }

        inv_x = bar_x - (item_side - bar_side) / 2;
        inv_y = bar_y - (item_side - bar_side) / 2;
        LinkedHashMap<String, Item> items = player.getItems();
        for (String key : items.keySet()) {
            Item item = items.get(key);
            item.getImage().draw(inv_x, inv_y);
            inv_x += bar_side + gap;
        }
    }
}
