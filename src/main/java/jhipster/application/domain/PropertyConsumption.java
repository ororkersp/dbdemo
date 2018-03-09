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
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PropertyConsumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "electricity_average")
    private Integer electricityAverage;

    @Column(name = "get_average")
    private Integer getAverage;

    @OneToOne
    @JoinColumn(unique = true)
    private PropertyConsumptionKey id;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getGetAverage() {
        return getAverage;
    }

    public PropertyConsumption getAverage(Integer getAverage) {
        this.getAverage = getAverage;
        return this;
    }

    public void setGetAverage(Integer getAverage) {
        this.getAverage = getAverage;
    }

    public PropertyConsumptionKey getId() {
        return id;
    }

    public PropertyConsumption id(PropertyConsumptionKey propertyConsumptionKey) {
        this.id = propertyConsumptionKey;
        return this;
    }

    public void setId(PropertyConsumptionKey propertyConsumptionKey) {
        this.id = propertyConsumptionKey;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PropertyConsumption propertyConsumption = (PropertyConsumption) o;
        if (propertyConsumption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyConsumption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyConsumption{" +
            "id=" + getId() +
            ", electricityAverage=" + getElectricityAverage() +
            ", getAverage=" + getGetAverage() +
            "}";
    }
}
