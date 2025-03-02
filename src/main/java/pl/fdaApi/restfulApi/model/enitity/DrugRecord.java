package pl.fdaApi.restfulApi.model.enitity;

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
    @NotNull(message = "Application number was not provided")
    @NotBlank(message = "Application number empty")
    private String applicationNumber;

    @Column(name = "manufacturer_name")
    @NotNull(message = "Manufacturer name was not provided")
    @NotBlank(message = "Manufacturer field empty")
    private String manufacturerName;

    @Column(name = "substance_name")
    @NotNull(message = "Substance name was not provided")
    @NotBlank(message = "Substance name missing")
    private String substanceName;

    @Column(name = "product_number")
    @NotNull(message = "Product numbers was not provided")
    @NotBlank(message = "Product number missing")
    @Pattern(regexp = "^[0-9]{3,}")
    private String productNumber;
}
