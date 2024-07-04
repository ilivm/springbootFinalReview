package danielkaiser.gss.challenge.liquibase;


import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

@UtilityClass
@Log4j2
public final class CsvImportUtil {

    public <T> List<T> loadObjectList(Class<T> type, String fileName, Charset encoding) {
        log.debug("Loading file {}", fileName);
        try (InputStream inputStream = new ClassPathResource(fileName, type.getClassLoader()).getInputStream()) {
            return loadObjectList(type, inputStream, encoding, getDefaultSchema());
        } catch (final IOException e) {
            log.error(String.format("Error occurred while loading object list from file %s", fileName), e);
            throw new IllegalStateException("Error occurred while loading object list from file " + fileName, e);
        }
    }

    private <T> List<T> loadObjectList(Class<T> type, InputStream inputStream, Charset encoding, CsvSchema schema) {
        Reader reader = new InputStreamReader(inputStream, encoding);
        return loadObjectList(type, reader, schema);
    }

    private <T> List<T> loadObjectList(Class<T> type, Reader reader, CsvSchema schema) {
        CsvMapper mapper = new CsvMapper();
        Module javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        return loadObjectList(type, reader, schema, mapper);
    }

    private <T> List<T> loadObjectList(Class<T> type, Reader reader, CsvSchema schema, @NonNull final CsvMapper mapper) {
        ObjectReader objectReader = mapper.readerFor(type).with(schema);
        try (MappingIterator<T> values = objectReader.readValues(reader)) {
            return values.readAll();
        } catch (IOException e) {
            log.error("Error occurred while loading object list from stream.", e);
            throw new IllegalStateException("Error occurred while loading object list from stream.", e);
        }
    }

    private CsvSchema getDefaultSchema() {
        return CsvSchema.emptySchema().withHeader().withColumnSeparator(';').withNullValue("");
    }
}

