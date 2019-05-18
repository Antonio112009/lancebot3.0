package discord;

import entities.Data;
import net.dv8tion.jda.core.entities.Message;

public class Special {

    private Data data;

    public Special(Data data) {
        this.data = data;
    }

    public void deleteMessages() {
        int until = Integer.parseInt(data.getCommand()[1]) + 1;

        for (Message mes : data.getChannel().getIterableHistory()) {
            if (until == 0)
                break;
            try {
                data.getChannel().deleteMessageById(mes.getId()).queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            until--;
        }
    }
}
