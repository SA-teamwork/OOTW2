package role.unit.Villager;

import role.unit.Player;

public class BedivereProduct implements VillagersProduct {

    private String chats[] = new String[]{
            "請尋找長生不老藥來治癒國王",
            "靈丹妙藥!我父親的病痊癒了!謝謝"
    };
    private String chat = "";

    @Override
    public String chat(Player player) {
        if (!player.hasElixir()) {
            chat = chats[0];
        } else {
            chat = chats[1];
            player.removeItem("elixir");
        }
        return chat;
    }

}
