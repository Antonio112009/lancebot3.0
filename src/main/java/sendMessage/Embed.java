package sendMessage;

import entities.Data;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;
import java.time.Instant;

public class Embed {

    private Data data;
    private EmbedBuilder embed = new EmbedBuilder();

    public Embed(Data data) {
        this.data = data;
    }

    public void sendDefaultAudit(){
        String description = data.getComment()[1];
        String title = data.getComment()[0].substring(7);
        sendNotification(data.getGuild().getTextChannelsByName("lance_audit", true).get(0), title, description);
    }

    public void sendNotification(MessageChannel channel, String title, String text){
        embed.setColor(new Color(255,255,0));
        embed.setAuthor(data.getMember().getEffectiveName(), null , data.getMember().getUser().getAvatarUrl());
        if (!title.isEmpty())
            embed.setTitle(title);
        embed.setDescription(text);
        embed.setTimestamp(Instant.now());
        channel.sendMessage(embed.build()).queue();
    }

}
