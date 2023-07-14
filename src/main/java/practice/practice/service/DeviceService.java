package practice.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.practice.domain.Device;
import practice.practice.domain.cache.ScheduleCache;
import practice.practice.domain.dto.CommonDto;
import practice.practice.feign.DeviceFeignClient;
import practice.practice.repo.DeviceRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceFeignClient deviceFeignClient;

    public Long sendTelemetry(String accessToken, CommonDto.TelemetryMsgDto telemetries) throws Exception {

        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append(telemetries.getList().stream()
                .map(telemetry -> "{\"" + telemetry.getKey() + "\":" + telemetry.getValue() + "}")
                .collect(Collectors.joining(","))
        );
        builder.append("}");

        return deviceFeignClient.sendTelemetry(accessToken, builder.toString());
    }

    public Long sendAttribute(String accessToken, CommonDto.AttributeMsgDto attributes) throws Exception {

        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append(attributes.getList().stream()
                .map(attribute -> "{\"" + attribute.getKey() + "\":\"" + attribute.getStatus() + "\"}")
                .collect(Collectors.joining(",")));
        builder.append("}");

        return deviceFeignClient.sendAttribute(accessToken, builder.toString());
    }
}
