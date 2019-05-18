package entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Players {

    private long discord_id;

    private LocalDate joined_clan;

    private String main_role;

    private long steam64_id;

    private String group_name;
}
