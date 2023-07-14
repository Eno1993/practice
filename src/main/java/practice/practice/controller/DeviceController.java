package practice.practice.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import practice.practice.domain.dto.CommonDto;
import practice.practice.domain.en.DeviceType;
import practice.practice.domain.en.MsgType;
import practice.practice.repo.DeviceRepository;
import practice.practice.service.DeviceService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;

    @PostMapping("/add")
    public Long addDevice(@RequestParam("accessToken") String accessToken,
                          @RequestParam("name") String name,
                          @RequestParam("deviceType")DeviceType deviceType){

        return deviceRepository.addDevice(accessToken, name, deviceType);
    }

    @PostMapping("/send/telemetry")
    public Long sendTelemetry(@RequestParam("accessToken") String accessToken,
                              @RequestParam("telemetries") CommonDto.TelemetryMsgDto telemetries) throws Exception {
        return deviceService.sendTelemetry(accessToken, telemetries);
    }

    @PostMapping("/send/attribute")
    public Long sendAttribute(@RequestParam("accessToken") String accessToken,
                              @RequestParam("attributes") CommonDto.AttributeMsgDto attributes) throws Exception {
        return deviceService.sendAttribute(accessToken, attributes);
    }


    /*@PostMapping("/sendData")
    public void sendData(@RequestParam("msgType") MsgType msgType,
                         @RequestParam("msg") List<CommonDto.TelemetryMsg> telemetryMsgList,
                         @RequestParam("metaData") List<CommonDto.MetaData> metaDateList){

    }*/


}
