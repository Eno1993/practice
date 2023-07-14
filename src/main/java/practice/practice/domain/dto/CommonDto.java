package practice.practice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class CommonDto {

    @AllArgsConstructor
    public static class MetaData{
        private String key;
        private String value;

    }

    @Data
    @AllArgsConstructor
    public static class TelemetryMsg{
        private String key;
        private Double value;
    }

    @Data
    @NoArgsConstructor
    public static class TelemetryMsgDto{
        private List<TelemetryMsg> list = new ArrayList<>();

    }

    @Data
    @AllArgsConstructor
    public static class AttributeMsg{
        private String key;
        private String status;
    }

    @Data
    @NoArgsConstructor
    public static class AttributeMsgDto{
        private List<AttributeMsg> list = new ArrayList<>();
    }
}
