package danielkaiser.gss.challenge.mapper;

import danielkaiser.gss.challenge.controller.dto.CustomerCreatedDto;
import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.controller.dto.CustomerDto;
import danielkaiser.gss.challenge.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(expression = "java( dto.getFirstName() + \" \" + dto.getLastName() )", target = "name")
    @Mapping(target = "id", ignore = true) // set by Hibernate
    @Mapping(target = "insuranceNumber", ignore = true)
        // set by business logic
    Customer.CustomerBuilder toEntityBuilder(CustomerCreationDto dto);

    @Mapping(target = "paymentRate", ignore = true)
        // calculated by business logic
    CustomerDto.CustomerDtoBuilder toDtoBuilder(Customer customer);

    CustomerCreatedDto toCreatedDto(Customer customer);

    default Customer.CustomerBuilder builder() {
        return Customer.builder();
    }

    default CustomerDto.CustomerDtoBuilder dtoBuilder() {
        return CustomerDto.builder();
    }
}
