package danielkaiser.gss.challenge.liquibase;

import danielkaiser.gss.challenge.domain.Customer;
import danielkaiser.gss.challenge.util.GenerateShortUuid;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomerImportTask extends AbstractCsvImportTask<Customer> {

    @Override
    protected Class<Customer> getImportTypeClass() {
        return Customer.class;
    }

    @Override
    protected Object mapToEntityInstance(Customer customer) {
        final String insuranceNumber = GenerateShortUuid.generate(8);
        customer.setInsuranceNumber(insuranceNumber);

        log.info("Saving customer with name {} and insurance number {}", customer.getName(), insuranceNumber);

        return customer;
    }

}
