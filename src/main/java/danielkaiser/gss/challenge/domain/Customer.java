package danielkaiser.gss.challenge.domain;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@Jacksonized
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Customer {

    @Id
    @Nullable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String insuranceNumber;

    private LocalDate dateOfBirth;

    private LocalDate inceptionOfPolicy;

}
