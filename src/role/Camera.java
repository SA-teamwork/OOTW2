package role;

import role.unit.Player;

/**
 * Represents the camera that controls our viewpoint.
 */
public class Camera {

    /**
     * The unit this camera is following
     */
    private Player unitFollow;

    /**
     * The width and height of the screen, Screen width, in pixels.
     */
    private int screenwidth;
    /**
     * Screen height, in pixels.
     */
    private int screenheight;

    /**
     * The camera's position in the world, in x and y coordinates.
     */
    private int xPos;
    private int yPos;

    /**
     * Create a new Camera object.
     *
     * @param player       The player to follow
     * @param screenwidth  The width of the viewport in pixels
     * @param screenheight The height of the viewport in pixels
     */
    public Camera(Player player, int screenwidth, int screenheight) {
        this.unitFollow = player;
        this.screenwidth = screenwidth;
        this.screenheight = screenheight;
    }

    public Camera(Camera c, Player player) {
        this.unitFollow = player;
        this.screenwidth = c.screenwidth;
        this.screenheight = c.screenheight;
        this.xPos = c.xPos;
        this.yPos = c.yPos;
    }

    /**
     * Update the game camera to re-centre its viewpoint around the player
     */
    public void update() {
        // Update the camera based on the player's position
        updatePos((int) unitFollow.getPosx(), (int) unitFollow.getPosy());
    }

    public void updatePos(int x, int y) {
        xPos = x - (screenwidth / 2);
        yPos = y - (screenheight / 2);
    }

    /**
     * Returns the minimum x value on screen
     */
    public int getMinX() {
        return xPos;
    }

    /**
     * Returns the maximum x value on screen
     */
    public int getMaxX() {
        return xPos + screenwidth;
    }

    /**
     * Returns the minimum y value on screen
     */
    public int getMinY() {
        return yPos;
    }

    /**
     * Returns the maximum y value on screen
     */
    public int getMaxY() {
        return yPos + screenheight;
    }

    /**
     * Tells the camera to follow a given unit.
     *
     * @param unit The new unit to follow
     */
    public void followUnit(Object unit) {
        if (unit instanceof Player) {
            unitFollow = (Player) unit;
        }
    }

}