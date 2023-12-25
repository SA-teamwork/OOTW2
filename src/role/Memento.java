package role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Memento {
    private World world;
    private String t;
    private int pw;

    public int setWorld(World w) {
        LocalDateTime t = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.t = dtf.format(t);
        this.world = w.clone();
        this.pw = String.valueOf(System.currentTimeMillis()).hashCode();
        return this.pw;
    }

    public World getWorld(int pw) throws IllegalAccessException, CloneNotSupportedException {
        if (pw == this.pw) {
            return this.world.clone();
        } else {
            throw new IllegalAccessException("permission denied!!");
        }
    }

    public String getT() {
        return t;
    }
}
