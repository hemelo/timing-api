package org.hemelo.timing.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hemelo.timing.converter.DurationConverter;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_spent")
@Data
public class TimeSpent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "app_id")
    private String appId;

    @Column(name = "started_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startedAt;

    @Column(name = "time_spent")
    @Convert(converter = DurationConverter.class)
    private Duration timeSpent;

}
