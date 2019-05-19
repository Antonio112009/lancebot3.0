package entities;


import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

import java.util.List;

@lombok.Data
public class Data {

    private MessageReceivedEvent event;
    private Guild guild;
    private GuildController controller;
    private String content;
    private String messageId;
    private MessageChannel channel;
    private Message message;
    private Member member;
    private List<Member> mentionedMembers;
    private String authorId;
    private String[] command;
    private String[] comment;
    private TextChannel lanceAudit;
    private TextChannel lanceNews;
    private TextChannel lanceOfficer;
    private Role lanceRole;
    private boolean mentioned = true;

    public Data(MessageReceivedEvent event) {

        this.event = event;
        this.guild = event.getGuild();
        this.controller = guild.getController();
        this.content = event.getMessage().getContentRaw().toLowerCase().replaceAll("\\s{2,}", " ").trim();
        this.channel = event.getChannel();
        this.member = event.getMember();
        this.command = content.split(" ");
        this.comment = content.split("\\+\\+");
        this.authorId = event.getAuthor().getId();
        this.messageId = event.getMessageId();
        this.message = event.getMessage();

        this.lanceAudit = event.getGuild().getTextChannelsByName("lance_audit", true).get(0);
        this.lanceNews = event.getGuild().getTextChannelsByName("lance_news", true).get(0);
        this.lanceOfficer = event.getGuild().getTextChannelsByName("lance_officer", true).get(0);
        this.lanceRole = event.getGuild().getRolesByName("Lance", true).get(0);
        try {
            this.mentionedMembers = event.getMessage().getMentionedMembers();
        } catch (Exception e) {
            mentioned = false;
        }
    }

    public Role roleByName(String role){
        return event.getGuild().getRolesByName(role, true).get(0);
    }
}
