package ru.example.department.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.example.department.model.OfficeEntity;
import ru.example.department.repositories.OfficeRepository;
import java.util.List;

@Component
@Slf4j
public class ScheduledTasks {

    private OfficeRepository officeRepository;

    public ScheduledTasks(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void reportCurrentTime() {

        List<OfficeEntity> offices =  officeRepository.findAll();
        for (OfficeEntity office : offices) {
            Double value = office.getValue();
            log.info("----------------------------------------- {}", office.getAddress());
            log.info(" value  {}", value);
            double percent = value * 0.01;
            office.setValue(value - percent);
            log.info(" value  {}, percent {}", office.getValue(), percent);
            log.info("-----------------------------------------");
        }

        officeRepository.saveAll(offices);

    }

}
