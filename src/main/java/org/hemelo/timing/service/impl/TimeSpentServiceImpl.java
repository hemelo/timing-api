package org.hemelo.timing.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hemelo.timing.dto.other.TimeSpentDto;
import org.hemelo.timing.model.TimeSpent;
import org.hemelo.timing.repository.TimeSpentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
public class TimeSpentServiceImpl implements org.hemelo.timing.service.TimeSpentService {

    @Autowired
    private TimeSpentRepository timeSpentRepository;

    @Override
    public void insertTimeSpent(TimeSpentDto timeSpentDto) {

        try {

            TimeSpent timeSpent = new TimeSpent();
            timeSpent.setAppId(timeSpentDto.appId());
            timeSpent.setUrl(timeSpentDto.url());

            if (timeSpentDto.startedAt() != null) {
                LocalDateTime startedAt = LocalDateTime.ofInstant(timeSpentDto.startedAt(), ZoneId.systemDefault());
                timeSpent.setStartedAt(startedAt);
            }

            timeSpent.setTimeSpent(timeSpentDto.timeSpent());
            timeSpentRepository.save(timeSpent);

            log.debug("Time spent inserted: {}", timeSpent);
        } catch (Exception e) {
            log.error("Error inserting time spent", e);
        }
    }
}
