package role.unit.Villager;

import role.unit.Player;

public class GuinevereProduct implements VillagersProduct {

    private String chats[] = new String[]{
            "如果你需要治癒就回到我身邊",
            "你現在看起來更健康了"
    };

    private String chat = "";

    @Override
    public String chat(Player player) {
        if (player.getHP() < player.getMAX_HP()) {
            chat = chats[1];
            // 加血
            player.setHP(player.getMAX_HP());
        } else {
            chat = chats[0];
        }

        return chat;

    }

}
