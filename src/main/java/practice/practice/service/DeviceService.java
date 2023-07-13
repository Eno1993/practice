package practice.practice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.practice.domain.Device;
import practice.practice.domain.cache.ScheduleCache;
import practice.practice.repo.DeviceRepository;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

}
