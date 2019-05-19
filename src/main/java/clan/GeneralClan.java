package clan;

import database.Database;
import entities.Data;
import entities.Players;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class GeneralClan {

    private Data data;

    public GeneralClan(Data data) {
        this.data = data;
    }

    public void showClanList(){
        List<Players> playersList = new Database().getPlayers();
        StringBuilder list = new StringBuilder();
        StringBuilder roleList = new StringBuilder();

        int index = 0;
        String[] roles = {"Командование", "Замком", "Офицер", "Сержант", "Механик", "Основной состав", "Рекрут"};

        for (String role : roles) {
            for (Players player : playersList) {
                if (player.getMain_role().equals(role)) {
                    index++;
                    roleList.append(index).append(". **").append(data.getGuild().getMemberById(player.getDiscord_id()).getEffectiveName()).append("**\n");
                }
            }
            if(index > 0){
                list.append(role).append("\n");
                list.append(roleList);
                list.append("\n");
                roleList.setLength(0);
            }
            index = 0;
        }
        if(list.length() > 0){
            data.getChannel().sendMessage(list.toString()).queue();
        }
    }


    public void showProfile(){
        List<Players> playersList = new Database().getPlayers();
    }


    public void upgradeSoldier(String roleName, int r, int g, int b){

        Data data = new Data(event);

        if (!data.isMentioned()) {
            //TODO улучшить вывод ошибки на экран
            data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
            return;
        }

        List<Role> roles = new ArrayList<>(data.getMentionedMember().getRoles());

        for (Role roleList : data.getMentionedMember().getRoles()) {
            if (roleList.getName().startsWith(roleName[roleID])){
                data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " имеет роль \"" + roleName[roleID] + "\"\n").queue();
                return;
            }
        }
        if (data.getCommand().length == 2) {

            data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " игрок не имеет роль \"" + roleName[roleID] + "\"\n").queue();

        } else if ((data.getCommand().length > 3) && (data.getComment().length == 2)){
            Recruit recruit = new Recruit();
            recruit.removeRecruit(data.getMentionedMember().getUser().getId());

            for (int i = 0; i < roles.size(); i++){
                for(String rName : roleName)
                    if(roles.get(i).getName().equals(rName)) roles.remove(i);
            }
            roles.add(data.getRoleCustom(roleName[roleID]));
            data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();

            //TODO в зависимости выводить понижение или повышение должности
            embed.createMessageAudit(data.getLanceAudit(), data.getMember(), "Получение новой роли", "Игрок " + data.getMentionedMember().getAsMention() + " получил роль \"" + roleName[roleID] + "\"\nПричина: " + data.getComment()[1], r, g, b);



        } else {
            //TODO написать вывод ошибки о неправильно введенной команде
            data.getChannel().sendMessage("Команда была введена неправильно").queue();
        }
    }
}
