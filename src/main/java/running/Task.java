package running;

import database.Database;
import entities.Recruit;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import sendMessage.Embed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Task {

    private JDA api;
    private TextChannel news;
    private TextChannel bot;
    private TextChannel officers;
    private Guild guild;

    Task(JDA api) {
        this.api = api;

        try {
            officers = api.awaitReady().getTextChannelsByName("lance_officer", true).get(0);
            bot = api.awaitReady().getGuilds().get(0).getTextChannelsByName("lance_bot",true).get(0);
            news = api.awaitReady().getGuilds().get(0).getTextChannelsByName("lance_news", true).get(0);
            guild = api.awaitReady().getGuilds().get(0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void taskRecruit(){
        List<Recruit> recruitList = new Database().getRecruits();

        HashMap<Long, Recruit> stringOutput = new HashMap<>();

        if (recruitList.size() == 0) {
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

        int numberRecruits = 0;
        StringBuilder text = new StringBuilder("Срок рекрутства у представленных ниже игроков подошел к концу.\n" +
                "Рассмотрите вопрос о переводе в основной состав, продлении рекрутства или исключении из клана.\n");
        for(Map.Entry<Long, Recruit> recruits : stringOutput.entrySet()) {

            Long key = recruits.getKey();
            Recruit value = recruits.getValue();

            if(Period.between(LocalDate.now(), value.getFinish_date()).getDays() <= 0){
                text.append(api.getGuilds().get(0).getMemberById(key).getEffectiveName()).append("\n");
            }

            numberRecruits++;
        }
        if(numberRecruits > 0)
            new Embed().sendNotification(officers, guild.getSelfMember(), "Истек срок рекрутства", text.toString());
    }



    void eraseLanceBot() {
        if(LocalTime.now().getHour() == 0 && LocalTime.now().getMinute() == 0) {
            System.out.println("Очистка чата");
            for (Message mes : api.getGuilds().get(0).getTextChannelsByName("lance_bot", true).get(0).getIterableHistory()) {
                try {
                    bot.deleteMessageById(mes.getId()).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            bot.sendMessage("Чат очищен").queue();
            System.out.println("Чат очищен");
        }
    }
}
