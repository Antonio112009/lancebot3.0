package clan;

import database.Database;
import entities.Data;
import entities.Players;
import net.dv8tion.jda.core.entities.Role;
import sendMessage.Embed;

import java.util.ArrayList;
import java.util.List;

public class GeneralClan {

    private String[] roleID = {"Рекрут", "Основной состав", "Сержант", "Механик", "Офицер", "Замком", "Командование"};
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

        //checking if member is mentioned
        if (!data.isMentioned()) {
            //TODO улучшить вывод ошибки на экран
            data.getChannel().sendMessage("Вы забыли упомянуть человека").queue();
            return;
        }

        List<Role> roles = new ArrayList<>(data.getMentionedMembers().get(0).getRoles());


        for (Role roleList : data.getMentionedMember().getRoles()) {

            //check if player is on Holiday
            if (roleList.getName().startsWith("Запас")){
                data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " имеет роль \"" + roleName + "\". Для начала выведите его из Запаса").queue();
                return;
            }

            //check if member has current role (fuck MySQL checker =) )
            if (roleList.getName().startsWith(roleName)){
                data.getChannel().sendMessage("Игрок " + data.getMentionedMember().getAsMention() + " имеет роль \"" + roleName + "\"\n").queue();
                return;
            }
        }


        if ((data.getCommand().length > 3) && (data.getComment().length == 2)){
            String roleNameOld = "";
            for (int i = 0; i < roles.size(); i++){
                for(String rName : roleID)
                    if(roles.get(i).getName().equals(rName)) {
                        roleNameOld = roles.get(i).getName();
                        roles.remove(i);
                    }
            }

            roles.add(data.roleByName(roleName));
            data.getController().modifyMemberRoles(data.getMentionedMember(), roles).queue();

            //database edits
            new Database().updatePlayerRole(data.getMentionedMemberID(), roleName);
            new Database().deleteRecruit(data.getMentionedMemberID());

            //в зависимости выводится понижение или повышение должности
            String upgradeReason = "повышен";
            for(String role : roleID){
                if(role.equals(roleName)){
                    upgradeReason = "понижен";
                    break;
                }
                if(role.equals(roleNameOld)){
                    upgradeReason = "повышен";
                    break;
                }
            }

            new Embed(data).sendMessageAudit(data.getMember(), "Получение новой роли", "Игрок " + data.getMentionedMember().getAsMention() + " " + upgradeReason + " до " + roleName + "\"\nПричина: " + data.getComment()[1], r, g, b);
            data.getChannel().sendMessage("Успешно " + upgradeReason+"!").queue();


        } else {
            data.getChannel().sendMessage("Команда была введена неправильно").queue();
        }
    }


    public void showHelp(){

    }
}
