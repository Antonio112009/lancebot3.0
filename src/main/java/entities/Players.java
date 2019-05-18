package entities;

import lombok.Data;

import java.sql.Date;

@Data
public class Players {

    private long discord_id;

    private Date joined_clan;

    private String main_role;

    private long steam64_id;

    private String group_name;
}
