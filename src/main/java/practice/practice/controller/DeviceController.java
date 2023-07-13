package practice.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import practice.practice.domain.en.DeviceType;
import practice.practice.domain.en.TelemetryType;
import practice.practice.repo.DeviceRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @PostMapping("/add")
    public Long addDevice(@RequestParam("accessToken") String accessToken,
                          @RequestParam("name") String name,
                          @RequestParam("deviceType")DeviceType deviceType){

        return deviceRepository.addDevice(accessToken, name, deviceType);
    }


}
