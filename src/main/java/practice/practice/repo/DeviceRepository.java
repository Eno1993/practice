package practice.practice.repo;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import practice.practice.domain.Device;
import practice.practice.domain.QDevice;
import practice.practice.domain.en.DeviceType;
import practice.practice.domain.en.TelemetryType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EntityManager entityManager;
    private final JPAQueryFactory factory;


    public Device findByAccessToken(String accessToken){

        QDevice qDevice = QDevice.device;
        return factory.selectFrom(qDevice)
                .where(qDevice.accessToken.eq(accessToken))
                .fetchOne();
    }

    public Long addDevice(String accessToken,
                          String name,
                          DeviceType deviceType){

        Device device = new Device(accessToken, name, deviceType);
        entityManager.persist(device);
        return device.getId();
    }



}
