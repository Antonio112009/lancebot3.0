import config.BotConfig;
import listener.Administration;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class StartBot extends ListenerAdapter {

    public static void main(String[] args) {
        try {


            JDA api = new JDABuilder(AccountType.BOT)
                    .setToken(BotConfig.TOKEN)
                    .setAutoReconnect(true)
                    .setGame(Game.watching("!помощь"))
                    .build();
            api.addEventListener(new Administration());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
