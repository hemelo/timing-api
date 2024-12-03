package org.hemelo.timing.property;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "timing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimingProperties {

    private Cors cors = new Cors();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Cors {
        private List<String> allowedOrigins;
        private List<String> allowedMethods;
        private List<String> allowedHeaders;
        private List<String> exposedHeaders;
        private boolean allowCredentials;
        private long maxAge;
    }

}
