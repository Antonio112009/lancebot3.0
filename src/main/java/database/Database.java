package database;

import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private Connection connection;

    private Statement statement;
    private PreparedStatement preparedStatement;


    void newConnection(){
        try {
            String query;

            // Drop tables
            try {
                query = "drop table players";
                sendQuery(query);
            } catch (Exception ignored){}
            try {
                query = "drop table lanceAccess";
                sendQuery(query);
            } catch (Exception ignored){}
            try {
                query = "drop table recruit";
                sendQuery(query);
            } catch (Exception ignored){}
            try {
                query = "drop table holiday";
                sendQuery(query);
            } catch (Exception ignored){}
            try {
                query = "drop table playerRoles";
                sendQuery(query);
            } catch (Exception ignored){}



            query = "create table players" +
                    "(" +
                    "id bigint not null auto_increment," +
                    "discord_id bigint not null," +
                    "joined_clan varchar(255) not null," +
                    "main_role varchar(255) not null," +
                    "steam64_id bigint null," +
                    "group_name varchar(255) null," +
                    "primary key (id)) " +
                    "DEFAULT CHARSET=utf8;";

            sendQuery(query);

            query = "create table playerRoles" +
                    "(" +
                    "id bigint not null auto_increment," +
                    "discord_id bigint not null," +
                    "role VARCHAR(255) not null," +
                    "primary key (id)) " +
                    "DEFAULT CHARSET=utf8;";
            sendQuery(query);

            query = "create table recruit" +
                    "(" +
                    "id bigint not null auto_increment," +
                    "discord_id bigint not null," +
                    "start_date VARCHAR(255) not null," +
                    "finish_date VARCHAR(255) not null," +
                    "primary key (id)) " +
                    "DEFAULT CHARSET=utf8;";
            sendQuery(query);

            query = "create table holiday" +
                    "(" +
                    "id bigint not null auto_increment," +
                    "discord_id bigint not null," +
                    "start_date VARCHAR(255) not null," +
                    "finish_date VARCHAR(255) not null," +
                    "primary key (id)) " +
                    "DEFAULT CHARSET=utf8;";
            sendQuery(query);

            query = "create table lanceAccess" +
                    "(" +
                    "id bigint not null auto_increment," +
                    "role VARCHAR(255) not null," +
                    "access_low int(1) not null," +
                    "access_medium int(1) not null," +
                    "access_high int(1) not null," +
                    "primary key (id)) " +
                    "DEFAULT CHARSET=utf8;";
            sendQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Setters
    public int insertNewPlayer(long discord_id, long steamId64, Date joined_clan, String main_role, String group_name){
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `players` (`discord_id`, `steam64_id`, `joined_clan`, `main_role`, `group_name`) VALUES (?,?,?,?,?)");

            preparedStatement.setLong(1, discord_id);
            preparedStatement.setLong(2, steamId64);
            preparedStatement.setDate(3, joined_clan);
            preparedStatement.setString(4, main_role);
            preparedStatement.setString(5, group_name);


            return preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }


    public int insertNewRecruit(long discord_id, Date start_date, Date finish_date, String type, String reason){
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `recruit` (`discord_id`,  `start_date`, `finish_date`, `type`, `reason`) VALUES (?,?,?,?,?)");

            preparedStatement.setLong(1, discord_id);
            preparedStatement.setDate(2, start_date);
            preparedStatement.setDate(3, finish_date);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, reason);

            return preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }


    public int insertNewHoliday(long discord_id, Date start_date, Date finish_date, String type, String reason){
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `holiday` (`discord_id`,  `start_date`, `finish_date`, `type`, `reason`) VALUES (?,?,?,?,?)");

            preparedStatement.setLong(1, discord_id);
            preparedStatement.setDate(2, start_date);
            preparedStatement.setDate(3, finish_date);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, reason);

            return preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }


    public int insertNewPlayerRoles(long discord_id, String role){
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `playerRoles` (`discord_id`,  `role`) VALUES (?,?)");
            preparedStatement.setLong(1, discord_id);
            preparedStatement.setString(2, role);

            return preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }


    public int insertNewLanceAccess(String role, boolean access_low, boolean access_medium, boolean access_high){
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO `lanceAccess` (`role`,  `access_low`, `access_medium`, `access_high`) VALUES (?,?,?,?)");
            preparedStatement.setString(1, role);
            preparedStatement.setBoolean(2, access_low);
            preparedStatement.setBoolean(3, access_medium);
            preparedStatement.setBoolean(4, access_high);


            return preparedStatement.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }


    //Getters
    public List<Recruit> getRecruits(){
        return getRecruits("");
    }

    public List<Recruit> getRecruits(String additional){
        List<Recruit> list = new ArrayList<>();
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM lanceDb.recruit " + additional + " ORDER BY discord_id ASC";

            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Recruit recruit = new Recruit();
                recruit.setDiscord_id(resultSet.getLong("discord_id"));
                recruit.setStart_date(resultSet.getDate("start_date").toLocalDate());
                recruit.setFinish_date(resultSet.getDate("finish_date").toLocalDate());
                recruit.setType(resultSet.getString("type"));
                recruit.setReason(resultSet.getString("reason"));
                list.add(recruit);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        return list;
    }


    public List<Holiday> getHoliday(){
        return getHoliday("");
    }

    public List<Holiday> getHoliday(String additional){
        List<Holiday> list = new ArrayList<>();
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM lanceDb.holiday " + additional + " ORDER BY discord_id ASC";

            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Holiday holiday = new Holiday();
                holiday.setDiscord_id(resultSet.getLong("discord_id"));
                holiday.setStart_date(resultSet.getDate("start_date").toLocalDate());
                holiday.setFinish_date(resultSet.getDate("finish_date").toLocalDate());
                holiday.setType(resultSet.getString("type"));
                holiday.setReason(resultSet.getString("reason"));
                list.add(holiday);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        return list;
    }


    public List<Players> getPlayers(){
        return getPlayers("");
    }

    public List<Players> getPlayers(String additional){
        List<Players> list = new ArrayList<>();
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM lanceDb.players " + additional;

            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Players players = new Players();
                players.setDiscord_id(resultSet.getLong("discord_id"));
                players.setJoined_clan(resultSet.getDate("joined_clan").toLocalDate());
                players.setSteam64_id(resultSet.getLong("steam64_id"));
                players.setMain_role(resultSet.getString("main_role"));
                players.setGroup_name(resultSet.getString("group_name"));
                list.add(players);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        return list;
    }


    public List<PlayerRoles> getPlayerRoles(){
        return getPlayerRoles("");
    }

    public List<PlayerRoles> getPlayerRoles(String additional){
        List<PlayerRoles> list = new ArrayList<>();
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT * FROM lanceDb.playerRoles " + additional;

            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PlayerRoles playerRoles = new PlayerRoles();
                playerRoles.setDiscord_id(resultSet.getLong("discord_id"));
                playerRoles.setRole(resultSet.getString("role"));
                list.add(playerRoles);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        return list;
    }

    public List<LanceAccess> getLanceAccess(){
        return getLanceAccess("");
    }

    public List<LanceAccess> getLanceAccess(String additional){
        List<LanceAccess> list = new ArrayList<>();
        try {
            connection = DatabaseConnection.getConnection();

            String query = "SELECT * FROM lanceDb.lanceAccess " + additional;

            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LanceAccess lanceAccess = new LanceAccess();
                lanceAccess.setRole(resultSet.getString("role"));

                HashMap<String, Boolean> hmap = new HashMap<>();
                hmap.put("access_low", resultSet.getBoolean("access_low"));
                hmap.put("access_medium", resultSet.getBoolean("access_medium"));
                hmap.put("access_high", resultSet.getBoolean("access_high"));
                lanceAccess.setAccess(hmap);
                list.add(lanceAccess);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
        return list;
    }





    private boolean sendQuery(String query){
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();

            return statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) try { connection.close(); } catch (SQLException e) {e.printStackTrace();}
            if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {e.printStackTrace();}
        }
    }
}
