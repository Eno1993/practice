package practice.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import practice.practice.domain.cache.ScheduleCache;
import practice.practice.service.DeviceService;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleCache scheduleCache;

    @PostMapping("/start")
    public void startSchedule(){
        scheduleCache.start();
    }

    @PostMapping("/stop")
    public void stopSchedule(@RequestParam("accessToken") String accessToken){
        scheduleCache.stop(accessToken);
    }

    @PostMapping("/add")
    public long addSchedule(@RequestParam("accessToken") String accessToken,
                            @RequestParam("cycle") Long cycle){
        return scheduleCache.addSchedule(accessToken, cycle);
    }

    @DeleteMapping("/delete")
    public long deleteSchedule(@RequestParam("accessToken") String accessToken){
        return scheduleCache.deleteSchedule(accessToken);
    }



}
