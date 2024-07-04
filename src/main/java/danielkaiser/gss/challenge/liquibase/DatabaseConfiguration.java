package danielkaiser.gss.challenge.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.Optional;

@Configuration
@EnableJpaRepositories("danielkaiser.gss.challenge")
@EnableTransactionManagement
public class DatabaseConfiguration {


    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibaseWithContext();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.xml");
        return liquibase;
    }


    @Bean(name = "auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }
}
