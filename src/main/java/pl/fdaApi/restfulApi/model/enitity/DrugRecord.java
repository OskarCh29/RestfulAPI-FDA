package pl.fdaApi.restfulApi.model.enitity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;


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
    @NotBlank(message = "Application number was not provided")
    private String applicationNumber;

    @Column(name = "manufacturer_name")
    @NotBlank(message =  "Manufacturer name was not provided")
    private String manufacturerName;

    @Column(name = "substance_name")
    @NotBlank(message = "Substance name was not provided")
    private String substanceName;

    @JsonManagedReference
    @OneToMany(mappedBy = "drugRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductNumber> productNumbers;
}
