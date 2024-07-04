package danielkaiser.gss.challenge.controller;

import danielkaiser.gss.challenge.controller.dto.CustomerCreatedDto;
import danielkaiser.gss.challenge.controller.dto.CustomerCreationDto;
import danielkaiser.gss.challenge.controller.dto.CustomerDto;
import danielkaiser.gss.challenge.controller.dto.InsuranceNumberDto;
import danielkaiser.gss.challenge.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.MediaType.*;

/**
 * REST controller for managing customers.
 */
@RestController
@RequestMapping(value = "/api/customers", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Log4j2
public class CustomerResource {

    private static final String CUSTOMER_EXAMPLE = """
            {
              "id": 17,
              "firstName": "Daniel",
              "lastName": "Kaiser",
              "insuranceNumber": "58e71225"
              "dateOfBirth": "1980-08-04",
              "inceptionOfPolicy": "2010-01-01",
              "paymentRate": 270
            }""";

    private final CustomerService customerService;

    @Operation(summary = "Create customer", description = "Create a new customer with the given information and return his insurance number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful created, insurance number returned.",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InsuranceNumberDto.class), examples =
                    @ExampleObject(value = """
                            {
                              "firstName": "Daniel",
                              "lastName": "Kaiser",
                              "dateOfBirth": "1980-08-04",
                              "inceptionOfPolicy": "2010-01-01"
                            }""")
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input.", content = @Content(mediaType = TEXT_PLAIN_VALUE, examples = @ExampleObject(value = "Input data is not valid."))),
    })
    @PostMapping
    public ResponseEntity<InsuranceNumberDto> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomerCreationDto.class), examples =
            @ExampleObject(value = """
                    {
                      "insuranceNumber": "58e71225"
                    }""")
            ))
            @Valid @RequestBody CustomerCreationDto customerCreationDto) throws URISyntaxException {
        final CustomerCreatedDto result = customerService.createCustomer(customerCreationDto);
        return ResponseEntity.created(new URI("/api/customers/" + result.getId()))
                .body(InsuranceNumberDto.of(result.getInsuranceNumber()));
    }


    @Operation(summary = "Retrieve a customer", description = "Retrieve a customer using his insurance number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieved.",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomerDto.class), examples = @ExampleObject(value = CUSTOMER_EXAMPLE))
            ),
            @ApiResponse(responseCode = "404", description = "No customer with the supplied insurance number exists.", content = @Content(mediaType = ALL_VALUE, examples = @ExampleObject)),
    })
    @GetMapping(value = "{insuranceNumber}")
    @Transactional(readOnly = true)
    public ResponseEntity<CustomerDto> findCustomer(@Parameter(description = "The insurance number", examples = {@ExampleObject(value = "58e71225")})
                                                    @PathVariable @NotNull String insuranceNumber) {
        return ResponseEntity.of(customerService.findCustomer(insuranceNumber));
    }


    @Operation(summary = "Retrieve all customers", description = "Retrieve all customers with payment information as a list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CustomerDto.class, example = CUSTOMER_EXAMPLE))))
    })
    @GetMapping
    @Transactional(readOnly = true)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> retrieveAllCustomers() {
        return customerService.retrieveAllCustomers();
    }

}
