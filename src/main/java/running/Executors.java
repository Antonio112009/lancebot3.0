package running;

import net.dv8tion.jda.core.JDA;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Executors {

    private JDA api;

    public Executors(JDA api) {
        this.api = api;
    }

    public void start(){
        Task task = new Task(api);
        ScheduledExecutorService executorService = java.util.concurrent.Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(task::taskRecruit, initialDelay(), TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(task::eraseLanceBot, 0, 1, TimeUnit.MINUTES);
    }

    private long initialDelay(){
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        ZonedDateTime nextRun = now.withHour(10).withMinute(0).withSecond(0);
        if(now.compareTo(nextRun) > 0)
            nextRun = nextRun.plusDays(1);

        Duration duration = Duration.between(now, nextRun);
        return duration.getSeconds();

    }
}