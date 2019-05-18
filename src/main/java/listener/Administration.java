package listener;

import clan.GeneralClan;
import clan.RecruitClan;
import database.Database;
import discord.ChangeAccess;
import discord.Special;
import discord.TalkChannel;
import entities.Data;
import entities.LanceAccess;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import sendMessage.Embed;

public class Administration extends ListenerAdapter {

    private Database database = new Database();
    private Data data;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        data = new Data(event);

        if(event.getAuthor().isBot()) return;

//        System.out.println(event.getMessage().getContentRaw());

        if(!giveAccess(event, "access_low")) return;

        //        Списки клана
        if(data.getContent().equals("!список"))
            new GeneralClan(data).showClanList();

        if(data.getContent().equals("!список рекрут"))
            new RecruitClan(data).showRecruits();

        if(data.getContent().equals("!список запас"))
            new GeneralClan(data).showHolidays();

        if(!giveAccess(event, "access_medium")) return;

        else if (data.getContent().startsWith("!аудит "))
            new Embed(data).sendDefaultAudit();




        if(!giveAccess(event, "access_high")) return;

        if(data.getContent().equals("!доступ"))
            new ChangeAccess(data).showAccess();

        if(data.getContent().startsWith("!дел "))
            new Special(data).deleteMessages();

        if(data.getContent().startsWith("!talk "))
            new TalkChannel(data).startPrivateConversation();

        if(data.getContent().equals("!close"))
            new TalkChannel(data).stopPrivateConversation();

        if(data.getContent().equals("!exit")){
            event.getGuild().getTextChannelsByName("lance_bot", true).get(0).deleteMessageById(event.getMessageId()).queue();
            System.exit(1);
        }

    }









    //Даем права доступа
    private boolean giveAccess(MessageReceivedEvent event, String access_type){
        for (Role member_role : event.getMember().getRoles()){
            for(LanceAccess access : database.getLanceAccess()){
                if(member_role.getName().equals(access.getRole())){
                    return access.getAccess().get(access_type);
                }
            }
        }
        return false;
    }
}
