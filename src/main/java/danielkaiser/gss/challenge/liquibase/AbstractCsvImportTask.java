package danielkaiser.gss.challenge.liquibase;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Getter
@Setter
public abstract class AbstractCsvImportTask<T> implements CustomTaskChange {

    private String file;

    private String type;

    @Setter
    private static ApplicationContext applicationContext;

    @Override
    public final void execute(Database database) {
        TransactionTemplate transaction = getBean(TransactionTemplate.class);
        transaction.execute(status -> {
            final EntityManager entityManager = getBean(EntityManager.class);

            loadObjectList(getImportTypeClass(), getFile()).stream()
                    .map(this::mapToEntityInstance)
                    .forEach(entityManager::merge);

            return null;
        });
    }

    private List<T> loadObjectList(Class<T> type, String fileName) {
        return CsvImportUtil.loadObjectList(type, fileName, StandardCharsets.UTF_8);
    }

    protected <U> U getBean(Class<U> clazz) {
        return applicationContext.getBean(clazz);
    }

    protected abstract Class<T> getImportTypeClass();

    protected abstract Object mapToEntityInstance(T t);

    @Override
    @Nullable
    public String getConfirmationMessage() {
        return null;
    }

    @Override
    public void setUp() {
        // not required
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
        // not required
    }

    @Override
    @Nullable
    public ValidationErrors validate(Database database) {
        return null;
    }
}
