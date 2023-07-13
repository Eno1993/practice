package practice.practice.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.practice.domain.Device;
import practice.practice.domain.QDevice;
import practice.practice.domain.QTelemetry;
import practice.practice.domain.Telemetry;
import practice.practice.domain.en.TelemetryType;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TelemetryRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DeviceRepository deviceRepository;
    private final EntityManager entityManager;
    private final JPAQueryFactory factory;

    public void addTelemetry(String accessToken, TelemetryType telemetryType, String keyValue, String statusValue, Double analogValue) {

        Device device = deviceRepository.findByAccessToken(accessToken);

        if(device!=null){
            Telemetry newTelemetry = new Telemetry(device, telemetryType, keyValue, statusValue, analogValue);
            entityManager.persist(newTelemetry);
        }
    }

    @Transactional
    public long updateTelemetry(String accessToken, String keyValue, String newValue) {
        QTelemetry qTelemetry = QTelemetry.telemetry;

        Telemetry telemetry = factory.selectFrom(qTelemetry)
                .where(qTelemetry.device.accessToken.eq(accessToken).and(qTelemetry.keyValue.eq(keyValue)))
                .fetchOne();

        if(telemetry!=null){
            JPAUpdateClause update = factory.update(qTelemetry)
                    .where(qTelemetry.device.accessToken.eq(accessToken).and(qTelemetry.keyValue.eq(keyValue)));
            if(telemetry.getTelemetryType().equals(TelemetryType.ANALOG_VALUE)){
                update.set(qTelemetry.analogValue, Double.parseDouble(newValue));
            }else{
                update.set(qTelemetry.statusValue, newValue);
            }
            return update.execute();
        }
        return 0l;
    }


    public List<Telemetry> getTelemetryListByAccessToken(String accessToken){
        QDevice qDevice = QDevice.device;
        QTelemetry qTelemetry = QTelemetry.telemetry;

        return factory.selectFrom(qTelemetry)
                .innerJoin(qDevice)
                .on(qDevice.accessToken.eq(accessToken))
                .fetch();
    }


}
