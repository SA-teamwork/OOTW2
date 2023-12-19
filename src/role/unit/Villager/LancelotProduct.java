package role.unit.Villager;

import role.unit.Player;

public class LancelotProduct implements VillagersProduct {
    private String chats[] = new String[]{
            "穿過河流向西找到活力護身符。",
            "找到力量之劍，過橋向東，然後向南走。",
            "在暗影之地找到敏捷之書",
            "你已經找到了我所知道的所有寶藏。"
    };
    private String chat = "";

    @Override
    public String chat(Player player) {
        if (player.getItem("amulet") == null) {
            chat = chats[0];
        } else if (player.getItem("sword") == null) {
            chat = chats[1];
        } else if (player.getItem("tome") == null) {
            chat = chats[2];
        } else {
            chat = chats[3];
        }
        return chat;
    }

}
