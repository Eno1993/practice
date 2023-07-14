package practice.practice.repo;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import practice.practice.domain.Device;
import practice.practice.domain.QDevice;
import practice.practice.domain.QTelemetry;
import practice.practice.domain.Telemetry;
import practice.practice.domain.dto.TelemetryDto;
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

        QTelemetry qTelemetry = QTelemetry.telemetry;
        List<Telemetry> telemetryList = factory
                .selectFrom(qTelemetry)
                .where(qTelemetry.keyValue.eq(keyValue))
                .fetch();
        if(device==null){
            throw new IllegalStateException("device is null");
        }
        if(!telemetryList.isEmpty()){
            throw new IllegalStateException("keyValue is duplicated");
        }

        Telemetry newTelemetry = new Telemetry(device, telemetryType, keyValue, statusValue, analogValue);
        entityManager.persist(newTelemetry);


    }

    @Transactional
    public long updateTelemetry(String accessToken, String keyValue, String newValue) {
        QDevice qDevice = QDevice.device;
        QTelemetry qTelemetry = QTelemetry.telemetry;

        Telemetry telemetry = factory.selectFrom(qTelemetry)
                .innerJoin(qDevice)
                .on(qDevice.accessToken.eq(accessToken))
                .where(qTelemetry.keyValue.eq(keyValue))
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


    public List<TelemetryDto.TelemetryValue> getTelemetryListByAccessToken(String accessToken){
        QDevice qDevice = QDevice.device;
        QTelemetry qTelemetry = QTelemetry.telemetry;
        return factory
                .select(Projections.constructor(
                        TelemetryDto.TelemetryValue.class,
                        qTelemetry.keyValue,
                        qTelemetry.telemetryType,
                        qTelemetry.analogValue,
                        qTelemetry.statusValue
                ))
                .from(qTelemetry)
                .innerJoin(qDevice)
                .on(qDevice.accessToken.eq(accessToken))
                .fetch();

    }

}
