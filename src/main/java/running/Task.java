package running;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.LocalTime;

class Task {

    private JDA api;
    private TextChannel news;
    private TextChannel bot;
    private TextChannel officers;

    Task(JDA api) {
        this.api = api;

        try {
            officers = api.awaitReady().getTextChannelsByName("lance_officer", true).get(0);
            bot = api.awaitReady().getGuilds().get(0).getTextChannelsByName("lance_bot",true).get(0);
            news = api.awaitReady().getGuilds().get(0).getTextChannelsByName("lance_news", true).get(0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void taskRecruit(){

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
