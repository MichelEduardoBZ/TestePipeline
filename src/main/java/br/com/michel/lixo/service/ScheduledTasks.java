package br.com.michel.lixo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private CollectService collectService;

    @Scheduled(cron = "0 18 11 * * *")
    public void updateCollectionStatus() {
        LocalDate currentDate = LocalDate.now();
        collectService.processCollectionStatus(currentDate);
    }

}
