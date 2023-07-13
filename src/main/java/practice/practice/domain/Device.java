package practice.practice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import practice.practice.domain.en.DeviceType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("device")
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(name = "access_token", unique = true)
    private String accessToken;

    @Column(name = "name")
    private String name;
    @Column(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @OneToMany(targetEntity = Telemetry.class, mappedBy = "device", cascade = CascadeType.ALL)
    @JsonIgnore
    private final List<Telemetry> telemetryList = new ArrayList<>();

    public Device(String accessToken,
                  String name,
                  DeviceType deviceType){
        this.accessToken = accessToken;
        this.name = name;
        this.deviceType = deviceType;
    }


}
