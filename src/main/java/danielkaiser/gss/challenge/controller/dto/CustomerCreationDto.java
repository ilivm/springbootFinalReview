package danielkaiser.gss.challenge.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Jacksonized
@Builder
@ToString
public class CustomerCreationDto {
    @Schema(example = "Daniel", required = true)
    String firstName;
    @Schema(example = "Kaiser", required = true)
    String lastName;
    @Schema(example = "1980-08-04", required = true, description = "The birth date of the customer")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dateOfBirth;
    @Schema(example = "2010-01-01", required = true, description = "The date of the inception of the contract")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate inceptionOfPolicy;
}
