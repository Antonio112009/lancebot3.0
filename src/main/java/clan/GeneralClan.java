package clan;

import database.Database;
import entities.Data;
import entities.Players;

import java.util.List;

public class GeneralClan {

    private Data data;

    public GeneralClan(Data data) {
        this.data = data;
    }

    public void showClanList(){
        List<Players> playersList = new Database().getPlayers();
        StringBuilder list = new StringBuilder();
        StringBuilder roleList = new StringBuilder();

        int index = 0;
        String[] roles = {"Командование", "Замком", "Офицер", "Сержант", "Механик", "Основной состав", "Рекрут"};

        for (String role : roles) {
            for (Players player : playersList) {
                if (player.getMain_role().equals(role)) {
                    index++;
                    roleList.append(index).append(". **").append(data.getGuild().getMemberById(player.getDiscord_id()).getEffectiveName()).append("**\n");
                }
            }
            if(index > 0){
                list.append(role).append("\n");
                list.append(roleList);
                list.append("\n");
                roleList.setLength(0);
            }
            index = 0;
        }
        if(list.length() > 0){
            data.getChannel().sendMessage(list.toString()).queue();
        }
    }
}
