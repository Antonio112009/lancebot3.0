package clan;

import database.Database;
import entities.Data;
import entities.Holiday;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

public class HolidayClan {

    private Data data;

    public HolidayClan(Data data) {
        this.data = data;
    }

    //TODO: есть проблемы с запасом. А что если человек не первый раз в запасе?

    public void showHolidays(){
        List<Holiday> holidayList = new Database().getHoliday();

        StringBuilder text = new StringBuilder();

        HashMap<Long, Holiday> stringOutput = new HashMap<>();
        if (holidayList.size() == 0) {
            data.getChannel().sendMessage("На данный момент рекрутов нету.").queue();
            return;
        }

        for (Holiday holiday : holidayList){
            if(!stringOutput.containsKey(holiday.getDiscord_id())){
                stringOutput.put(holiday.getDiscord_id(), holiday);
            } else {
                Holiday recruit1 = stringOutput.get(holiday.getDiscord_id());
                recruit1.setFinish_date(holiday.getFinish_date());
                stringOutput.replace(holiday.getDiscord_id(), recruit1);
            }
        }

        for(Map.Entry<Long, Holiday> holidays : stringOutput.entrySet()) {
            Long key = holidays.getKey();
            Holiday value = holidays.getValue();

            text.append("Игрок: **").append(data.getGuild().getMemberById(key).getEffectiveName()).append("**\n");
            text.append("Начало запаса: **").append(value.getStart_date()).append("**\n");
            text.append("Конец запаса: **").append(value.getFinish_date()).append("**\n");
            text.append("Осталось: **").append(DAYS.between(LocalDate.now(), value.getFinish_date())).append("**\n\n");
        }

        data.getChannel().sendMessage(text).queue();
    }
}
