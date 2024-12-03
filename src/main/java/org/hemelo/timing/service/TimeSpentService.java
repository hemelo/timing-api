package org.hemelo.timing.service;

import org.hemelo.timing.dto.other.TimeSpentDto;

public interface TimeSpentService {
    void insertTimeSpent(TimeSpentDto timeSpentDto);
}
