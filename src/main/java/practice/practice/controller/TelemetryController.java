package practice.practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.practice.domain.en.TelemetryType;
import practice.practice.repo.TelemetryRepository;

@RestController
@RequestMapping("/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryRepository telemetryRepository;

    @PostMapping("/add")
    public void addTelemetry(@RequestParam("accessToken")String accessToken,
                             @RequestParam("telemetryType")TelemetryType telemetryType,
                             @RequestParam("keyValue")String keyValue,
                             @RequestParam(value = "statusValue", required = false)String statusValue,
                             @RequestParam(value = "analogValue", required = false)Double analogValue){

        telemetryRepository.addTelemetry(accessToken, telemetryType, keyValue, statusValue, analogValue);
    }

    @PostMapping("/update")
    public long updateTelemetry(@RequestParam("accessToken")String accessToken,
                                @RequestParam("keyValue")String keyValue,
                                @RequestParam("newValue")String newValue){
        return telemetryRepository.updateTelemetry(accessToken, keyValue, newValue);
    }


}
