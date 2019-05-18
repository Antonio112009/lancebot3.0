package clan;

import database.Database;
import entities.Data;
import entities.Recruit;

import java.time.LocalDate;
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
            text.append("Осталось: **").append(DAYS.between(LocalDate.now(), value.getFinish_date().toLocalDate())).append("**\n\n");
        }

        data.getChannel().sendMessage(text).queue();
    }
}