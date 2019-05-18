package clan;

import database.Database;
import entities.Data;
import entities.Holiday;
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

        int index = 0;
        String[] roles = {"Командование", "Замком", "Офицер", "Сержант", "Механик", "Основной состав", "Рекрут"};

        for (String role : roles) {
            list.append(role).append("\n");
            for (Players player : playersList) {
                if (player.getMain_role().equals(role)) {
                    index++;
                    list.append(index).append(". **").append(data.getGuild().getMemberById(player.getDiscord_id()).getEffectiveName()).append("**\n");
                }
            }
            list.append("\n");
        }
        data.getChannel().sendMessage(list.toString()).queue();
    }


    public void showHolidays(){
        List<Holiday> holidayList = new Database().getHoliday("WHERE id IN (SELECT MAX(id) FROM holiday GROUP BY discord_id)");
        data.getChannel().sendMessage("количество записей: " + holidayList.size()).queue();
    }


}
