package entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Holiday {
    private long discord_id;

    private LocalDate start_date;

    private LocalDate finish_date;

    private String type;

    private String reason;
}
