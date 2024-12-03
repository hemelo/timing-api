package org.hemelo.timing.dto.other;

import java.time.Duration;
import java.time.Instant;

public record TimeSpentDto(
        String url,
        String appId,
        Instant startedAt,
        Duration timeSpent
) {
}
