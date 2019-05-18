package discord;

import database.Database;
import entities.Data;
import entities.LanceAccess;

public class ChangeAccess {

    private Data data;
    private Database database = new Database();

    public ChangeAccess(Data data) {
        this.data = data;
    }


    /**
     * Show access to the table
     */

    public void showAccess(){
        String list = "```js\n+----------------+--------+--------+--------+\n";
        list += "| Роль           | low    | medium | high   |\n+----------------+--------+--------+--------+\n";

        String emptyList = "|                |        |        |        |\n";
        StringBuilder listBuilder = new StringBuilder(list);
        for(LanceAccess lanceAccess : database.getLanceAccess("ORDER BY id ASC")){

            StringBuilder stringBuild = new StringBuilder(emptyList);
            stringBuild.replace(2, lanceAccess.getRole().length() + 2, lanceAccess.getRole());
            stringBuild.replace(19, lanceAccess.getAccess().get("access_low").toString().length() + 19, lanceAccess.getAccess().get("access_low").toString());
            stringBuild.replace(28, lanceAccess.getAccess().get("access_medium").toString().length() + 28, lanceAccess.getAccess().get("access_medium").toString());
            stringBuild.replace(37, lanceAccess.getAccess().get("access_high").toString().length() + 37, lanceAccess.getAccess().get("access_high").toString());
            listBuilder.append(stringBuild);
        }
        listBuilder.append("+----------------+--------+--------+--------+\n```");
        data.getChannel().sendMessage(listBuilder.toString()).queue();

        //TODO: раширить данные  для вывода: какие кому команды доступны.
    }






}
