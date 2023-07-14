package practice.practice.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.practice.domain.en.TelemetryType;

import javax.persistence.*;

@Entity
@DiscriminatorValue("telemetry")
@NoArgsConstructor
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Device.class)
    private Device device;
    @Column(name = "telemetry_type")
    @Getter
    private TelemetryType telemetryType;
    @Column(name = "key_value")
    private String keyValue;
    @Column(name = "status_value")
    private String statusValue;
    @Column(name = "analog_value")
    private Double analogValue;

    public Telemetry(Device device,
                     TelemetryType telemetryType,
                     String keyValue,
                     String statusValue,
                     Double analogValue){
        this.device = device;
        this.telemetryType = telemetryType;
        this.keyValue = keyValue;
        this.statusValue = statusValue;
        this.analogValue = analogValue;
    }

}
