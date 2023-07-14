package practice.practice.domain.dto;

import practice.practice.domain.en.TelemetryType;

public class TelemetryDto {

    public static class TelemetryValue{
        private String key;
        private String value;
        public TelemetryValue(String key, TelemetryType telemetryType, Double analogValue, String statusValue){
            this.key = key;
            if(telemetryType.equals(TelemetryType.ANALOG_VALUE)){
                this.value = analogValue.toString();
            }else{
                this.value = statusValue;
            }

        }

        public String getJsonBody(){
            return "{\"" + this.key + "\":\"" + this.value +"\"}";
        }

    }

}
