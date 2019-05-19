package clan;

import database.Database;
import entities.Data;
import entities.Recruit;
import net.dv8tion.jda.core.entities.Role;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

public class RecruitClan {

    private Data data;

    public RecruitClan(Data data) {
        this.data = data;
    }

    public void showRecruits(){
        List<Recruit> recruitList = new Database().getRecruits();

        StringBuilder text = new StringBuilder();

        HashMap<Long, Recruit> stringOutput = new HashMap<>();
        if (recruitList.size() == 0) {
            data.getChannel().sendMessage("На данный момент рекрутов нету.").queue();
            return;
        }

        for (Recruit recruit : recruitList){
            if(!stringOutput.containsKey(recruit.getDiscord_id())){
                stringOutput.put(recruit.getDiscord_id(), recruit);
            } else {
                Recruit recruit1 = stringOutput.get(recruit.getDiscord_id());
                recruit1.setFinish_date(recruit.getFinish_date());
                stringOutput.replace(recruit.getDiscord_id(), recruit1);
            }
        }

        for(Map.Entry<Long, Recruit> recruits : stringOutput.entrySet()) {
            Long key = recruits.getKey();
            Recruit value = recruits.getValue();

            text.append("Игрок: **").append(data.getGuild().getMemberById(key).getEffectiveName()).append("**\n");
            text.append("Начало рекрутства: **").append(value.getStart_date()).append("**\n");
            text.append("Конец рекрутства: **").append(value.getFinish_date()).append("**\n");
            text.append("Осталось: **").append(DAYS.between(LocalDate.now(), value.getFinish_date())).append("**\n\n");
        }

        data.getChannel().sendMessage(text).queue();
    }


    public void addRecruit(){
        Database database = new Database();

        if(data.getMentionedMembers().size() != 1){
            //TODO: изменить на EmbedMessage!
            data.getChannel().sendMessage("Игрок не упомянут или упомянуто более одного игрока.").queue();
            return;
        }

        long discord_player = data.getMentionedMembers().get(0).getUser().getIdLong();

        if(database.checkIfPlayerExists(discord_player)){
            //TODO: изменить на EmbedMessage!
            data.getChannel().sendMessage("Данный игрок находится в составе клана").queue();
        } else {
            database.insertNewPlayer(discord_player, 123L, new Date(System.currentTimeMillis()), "Рекрут", "Soldier");
            long date_finish = LocalDate.now().plusMonths(2).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            database.insertNewRecruit(discord_player,new Date(System.currentTimeMillis()), new Date(date_finish), "Обычная", null);

            List<Role> roles = new ArrayList<>(data.getMentionedMembers().get(0).getRoles());
            roles.add(data.roleByName("Рекрут"));
            roles.add(data.roleByName("Lance"));

            data.getController().modifyMemberRoles(data.getMentionedMembers().get(0), roles).queue();
            data.getChannel().sendMessage("Новый рекрут принят в клан").queue();
        }
    }
}