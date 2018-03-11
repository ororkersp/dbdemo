package jhipster.application.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "property_consumption")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PropertyConsumption implements Serializable {

    @EmbeddedId
    private PropertyConsumptionKey id;

    @Column(name = "electricity_average")
    private Integer electricityAverage;

    @Column(name = "gas_average")
    private Integer gasAverage;

}
