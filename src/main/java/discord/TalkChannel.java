package discord;

import database.Database;
import entities.Data;
import entities.LanceAccess;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;

import java.util.Arrays;

import java.util.List;

/*
Что надо добавить?

Возможность добавлять и ислючать от одного до n юзеров

 */

public class TalkChannel {

    private Data data;

    public TalkChannel(Data data) {
        this.data = data;
        data.getChannel().deleteMessageById(data.getMessageId()).queue();
    }

    public void startPrivateConversation () {
        List<Permission> list = Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);

        String name = data.getEvent().getMessage().getMentionedMembers().get(0).getEffectiveName().toLowerCase().replaceAll(" ", "");

        data.getGuild().getCategoriesByName("Hall", true).get(0).createTextChannel(name).queue(
                channel -> {
                    channel.putPermissionOverride(data.getGuild().getPublicRole()).setDeny(list).queue();

                    for (Member member : data.getMentionedMembers()) {
                        try {
                            channel.putPermissionOverride(member).setAllow(list).queue();
                        } catch (Exception ignore){}
                    }

                    for(LanceAccess access : new Database().getLanceAccess("WHERE access_high=1")){
                        try {
                            channel.putPermissionOverride(data.getGuild().getRolesByName(access.getRole(), true).get(0)).setAllow(list).queue();
                        } catch (Exception ignore){}
                    }
                    channel.putPermissionOverride(data.getGuild().getSelfMember()).setAllow(list).queue();
                }
        );

        data.getGuild().getCategoriesByName("Hall", true).get(0).createVoiceChannel(name).queue(
                channel -> {
                    channel.putPermissionOverride(data.getGuild().getPublicRole()).setDeny(list).queue();

                    for (Member member : data.getMentionedMembers()) {
                        try {
                            channel.putPermissionOverride(member).setAllow(list).queue();
                        } catch (Exception ignore){}
                    }

                    for(LanceAccess access : new Database().getLanceAccess("WHERE access_high=1")){
                        try {
                            channel.putPermissionOverride(data.getGuild().getRolesByName(access.getRole(), true).get(0)).setAllow(list).queue();
                        } catch (Exception ignore){}
                    }
                    channel.putPermissionOverride(data.getGuild().getSelfMember()).setAllow(list).queue();
                }
        );
    }


    public void stopPrivateConversation(){
        if(data.getMessage().getChannel().getName().equals("получить-роль") || data.getMessage().getChannel().getName().equals("hall") || data.getMessage().getChannel().getName().equals("lance_recruit"))
            return;

        if(data.getMessage().getCategory().getName().equals("Hall")){
            try {
                if (data.getMessage().getCategory().getName().equals("Hall")) {
                    data.getMessage().getTextChannel().delete().queue();
                    data.getGuild().getVoiceChannelsByName(data.getMessage().getTextChannel().getName(), true).get(0).delete().queue();
                }
            } catch (Exception ignore){}
        }
    }
}


