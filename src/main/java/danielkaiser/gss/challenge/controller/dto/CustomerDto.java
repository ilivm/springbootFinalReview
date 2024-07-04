package danielkaiser.gss.challenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
@ToString
public class CustomerDto {
    @Schema(description = "The database ID", example = "1")
    Long id;
    @Schema(description = "The full name of the customer", example = "Daniel Kaiser", required = true)
    String name;
    @Length(min = 8, max = 8)
    @Schema(required = true)
    String insuranceNumber;
    @Schema(example = "1980-08-04", required = true, description = "The birth date of the customer")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;
    @Schema(example = "2010-01-01", required = true, description = "The date of the inception of the contract")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate inceptionOfPolicy;
    @Schema(description = "The monthly payment amount in Euro.", example = "270", required = true)
    BigDecimal paymentRate;
}
