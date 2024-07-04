package danielkaiser.gss.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Configuration for the system default clock, to be used in non test environments.
 */
@Configuration
public class ClockConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
