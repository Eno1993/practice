package practice.practice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "device-data", url = "${thingsboard.url}")
public interface DeviceFeignClient {


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/{accessToken}/telemetry",
            produces = "application/json"
    )
    Long sendTelemetry(@PathVariable("accessToken") String accessToken,
                       @RequestBody String payLoad) throws Exception;


    @RequestMapping(
            method = RequestMethod.POST,
            value = "/{accessToken}/attribute",
            produces = "application/json"
    )
    Long sendAttribute(@PathVariable("accessToken") String accessToken,
                       @RequestBody String payLoad) throws Exception;
}