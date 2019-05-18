package entities;

import lombok.Data;

import java.sql.Date;

@Data
public class Recruit {

    private long discord_id;

    private Date start_date;

    private Date finish_date;

    private String type;

    private String reason;

}
