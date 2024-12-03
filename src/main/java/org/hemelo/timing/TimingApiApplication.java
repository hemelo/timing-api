package org.hemelo.timing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("org.hemelo.timing.property")
public class TimingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimingApiApplication.class, args);
    }

}
