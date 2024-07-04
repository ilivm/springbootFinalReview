package danielkaiser.gss.challenge.service;

import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.mapper.CustomerMapperImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CustomerServiceTest {

    private final Clock testClock = Clock.fixed(Instant.parse("2020-12-22T10:15:30.00Z"), ZoneId.of("UTC"));

    private final CustomerService service = new CustomerService(new CustomerMapperImpl(), mock(CustomerRepository.class), testClock);

    @ParameterizedTest(name = "age={0}, expectedFactor={1}")
    @MethodSource("ageFactorProvider")
    void shouldProvideCorrectAgeFactor(int age, int expectedFactor) {
        assertThat(service.getAgeFactor(age)).isEqualTo(expectedFactor);
    }

    private static Stream<Arguments> ageFactorProvider() {
        return Stream.of(
                Arguments.of(0, 50),
                Arguments.of(11, 50),
                Arguments.of(12, 75),
                Arguments.of(21, 100),
                Arguments.of(45, 125),
                Arguments.of(48, 125),
                Arguments.of(67, 0),
                Arguments.of(100, 0)
        );
    }

    @ParameterizedTest(name = "birthDate={0}, expectedAge={1}")
    @MethodSource("birthDateProvider")
    void shouldCalculateAgeCorrectly(LocalDate birthDate, int expectedAge) {
        assertThat(service.calculateAgeOfCustomer(birthDate)).isEqualTo(expectedAge);
    }

    private static Stream<Arguments> birthDateProvider() {
        return Stream.of(
                Arguments.of(LocalDate.of(2020, 12, 12), 0),
                Arguments.of(LocalDate.of(2020, 12, 24), -1), // not born yet
                Arguments.of(LocalDate.of(2020, 12, 22), 0), // just born
                Arguments.of(LocalDate.of(2021, 12, 22), -1), // apparently in the future...
                Arguments.of(LocalDate.of(1980, 8, 24), 40),
                Arguments.of(LocalDate.of(1978, 8, 24), 42),
                Arguments.of(LocalDate.of(2019, 12, 22), 1),
                Arguments.of(LocalDate.of(2019, 12, 23), 0)
        );
    }

    @ParameterizedTest(name = "inceptionDate={0}, expected full years={1}")
    @MethodSource("fullYearsProvider")
    void shouldCalculateFullYearsCorrectly(LocalDate inceptionDate, int expectedFullYears) {
        assertThat(service.calculateFullYearsSinceInception(inceptionDate)).isEqualTo(expectedFullYears);
    }

    private static Stream<Arguments> fullYearsProvider() {
        return Stream.of(
                Arguments.of(LocalDate.of(2020, 12, 12), 0),
                Arguments.of(LocalDate.of(2019, 12, 22), 1),
                Arguments.of(LocalDate.of(2010, 1, 1), 10),
                Arguments.of(LocalDate.of(2010, 1, 31), 10),
                Arguments.of(LocalDate.of(2020, 12, 22), 0),
                Arguments.of(LocalDate.of(1980, 8, 24), 40),
                Arguments.of(LocalDate.of(1978, 8, 24), 42),
                Arguments.of(LocalDate.of(2019, 12, 22), 1),
                Arguments.of(LocalDate.of(2019, 12, 23), 1),
                Arguments.of(LocalDate.of(2019, 12, 31), 1)
        );
    }
}
