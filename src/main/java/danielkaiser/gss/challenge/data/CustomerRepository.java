package danielkaiser.gss.challenge.data;

import danielkaiser.gss.challenge.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);

    Optional<Customer> findByInsuranceNumber(String name);

}
