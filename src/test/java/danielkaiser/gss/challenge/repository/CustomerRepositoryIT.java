package danielkaiser.gss.challenge.repository;

import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldLoadCustomersFromCsv() {
        final List<Customer> customers = repository.findAll();
        assertThat(customers).hasSizeGreaterThanOrEqualTo(5); // five from liquibase and maybe more from other use of the app

        // all customer numbers have a length of 8 chars
        assertThat(customers).extracting(Customer::getInsuranceNumber).extracting(String::length).containsOnly(8);
    }
}
