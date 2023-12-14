package role.unit.Villager;

import org.newdawn.slick.SlickException;

public class Bedivere extends VillagersTemplete {

    public Bedivere(String image_path, double posx, double posy, int MAX_HP, int attack, int CoolDown, String name)
            throws SlickException {
        super(image_path, posx, posy, MAX_HP, attack, CoolDown, name);
    }

    public Bedivere(String image_path, int tw, int th, double posx, double posy, int MAX_HP, int attack, int CoolDown,
            String name) throws SlickException {
        super(image_path, tw, th, posx, posy, MAX_HP, attack, CoolDown, name);
    }

    private Bedivere(VillagersTemplete v) {
        super(v);
    }

    @Override
    public VillagersProduct genVillager() {
        return new BedivereProduct();
    }

    @Override
    public VillagersTemplete deepClone() {
        return new Bedivere(this);
    }
}
