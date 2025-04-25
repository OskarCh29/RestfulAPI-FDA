package pl.fda.restfulapi.model.enitity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "drug_record")
@Data
public class DrugRecord {
    @Id
    @Column(name = "application_number")
    @NotBlank(message = "Application number empty")
    private String applicationNumber;

    @Column(name = "manufacturer_name")
    @NotBlank(message = "Manufacturer field empty")
    private String manufacturerName;

    @Column(name = "substance_name")
    @NotBlank(message = "Substance name missing")
    private String substanceName;

    @Column(name = "product_number")
    @NotBlank(message = "Product number missing")
    @Pattern(regexp = "^[0-9]{3,}")
    private String productNumber;
}
