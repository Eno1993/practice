package practice.practice.domain.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;
import practice.practice.domain.Device;
import practice.practice.domain.Telemetry;
import practice.practice.feign.DeviceFeignClient;
import practice.practice.repo.DeviceRepository;
import practice.practice.repo.TelemetryRepository;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ScheduleCache {

    private static ThreadPoolTaskScheduler scheduler;
    private static Map<String, ScheduledFuture<?>> scheduleMap = new HashMap<>();
    private final DeviceRepository deviceRepository;
    private final TelemetryRepository telemetryRepository;
    private final DeviceFeignClient deviceFeignClient;

    public void start() {
        if(scheduler==null){
            scheduler = new ThreadPoolTaskScheduler();
            scheduler.initialize();
            scheduler.setPoolSize(10);
        }
    }

    public void stop(String accessToken){

        if(scheduler!=null){
            scheduler.shutdown();
            scheduler = null;
        }
    }

    public long addSchedule(String accessToken, Long cycle) {

        synchronized (scheduleMap){
            if(!scheduleMap.containsKey(accessToken)){
                ScheduledFuture<?> newSchedule = scheduler.schedule(getRunnable(accessToken),
                        new PeriodicTrigger(cycle, TimeUnit.SECONDS));
                scheduleMap.put(accessToken, newSchedule);
                return 1l;
            }
        }
        return 0l;
    }

    public long deleteSchedule(String accessToken) {
        if(scheduleMap.containsKey(accessToken)){
            scheduleMap.remove(accessToken, scheduleMap.get(accessToken));
            return 1l;
        }
        return 0l;
    }

    public Runnable getRunnable(String accessToken){
        return () ->{
            List<Telemetry> telemetryList = telemetryRepository.getTelemetryListByAccessToken(accessToken);

            telemetryList.stream()
                    .forEach(telemetry -> {
                        String jsonBody = "{\"key\":\"value\"}";
                        try {
                            deviceFeignClient.sendTelemetry(accessToken, jsonBody);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });

        };
    }
}
