package danielkaiser.gss.challenge.controller.dto;

import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
@Builder
public class CustomerCreatedDto {
    Long id;
    @Length(min = 8, max = 8)
    String insuranceNumber;
}
