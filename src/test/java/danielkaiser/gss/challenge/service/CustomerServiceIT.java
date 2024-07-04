package danielkaiser.gss.challenge.service;

import danielkaiser.gss.challenge.data.CustomerRepository;
import danielkaiser.gss.challenge.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceIT {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCalculateCorrectBaseRateForGivenCustomer() {
        final Customer thomasDanzig = customerRepository.findByName("Thomas Danzig").orElseThrow();
        assertThat(thomasDanzig).isNotNull();

        final BigDecimal rateForCustomer = customerService.calculateRateForCustomer(thomasDanzig);

        // According to the specification, Mr Danzig should have a rate of 273 € -- but as he actually has already finished ten years (and the specifications were written last year), this is corrected here
        assertThat(rateForCustomer).isEqualTo(BigDecimal.valueOf(270.0));
    }

}
