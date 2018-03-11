package jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PropertyConsumption.
 */
@Entity
@Table(name = "property_consumption")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PropertyConsumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PropertyConsumptionKey id;

    @Column(name = "electricity_average")
    private Integer electricityAverage;

    @Column(name = "gas_average")
    private Integer gasAverage;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public PropertyConsumptionKey getId() {
        return id;
    }

    public void setId(PropertyConsumptionKey id) {
        this.id = id;
    }

    public Integer getElectricityAverage() {
        return electricityAverage;
    }

    public PropertyConsumption electricityAverage(Integer electricityAverage) {
        this.electricityAverage = electricityAverage;
        return this;
    }

    public void setElectricityAverage(Integer electricityAverage) {
        this.electricityAverage = electricityAverage;
    }

    public Integer getGasAverage() {
        return gasAverage;
    }

    public PropertyConsumption gasAverage(Integer gasAverage) {
        this.gasAverage = gasAverage;
        return this;
    }

    public void setGasAverage(Integer gasAverage) {
        this.gasAverage = gasAverage;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyConsumption that = (PropertyConsumption) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(electricityAverage, that.electricityAverage) &&
            Objects.equals(gasAverage, that.gasAverage);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, electricityAverage, gasAverage);
    }
}
