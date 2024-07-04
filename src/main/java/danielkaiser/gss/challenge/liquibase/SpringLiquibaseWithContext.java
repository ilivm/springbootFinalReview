package danielkaiser.gss.challenge.liquibase;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.util.StopWatch;

public class SpringLiquibaseWithContext extends SpringLiquibase implements ApplicationContextAware {

    private final Logger logger = LogManager.getLogger();

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        AbstractCsvImportTask.setApplicationContext(applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        logger.info("Starting Liquibase synchronously");

        final StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();

        logger.info("All Liquibase migrations finished successfully in {} ms", watch.getTotalTimeMillis());
    }
}
