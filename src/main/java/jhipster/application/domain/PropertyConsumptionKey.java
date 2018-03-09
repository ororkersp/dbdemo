package jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PropertyConsumptionKey.
 */
@Entity
@Table(name = "property_consumption_key")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PropertyConsumptionKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "number_of_peole")
    private Integer numberOfPeole;

    @Column(name = "electricity_average")
    private Integer electricityAverage;

    @Column(name = "get_average")
    private Integer getAverage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public PropertyConsumptionKey propertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public PropertyConsumptionKey numberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
        return this;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Integer getNumberOfPeole() {
        return numberOfPeole;
    }

    public PropertyConsumptionKey numberOfPeole(Integer numberOfPeole) {
        this.numberOfPeole = numberOfPeole;
        return this;
    }

    public void setNumberOfPeole(Integer numberOfPeole) {
        this.numberOfPeole = numberOfPeole;
    }

    public Integer getElectricityAverage() {
        return electricityAverage;
    }

    public PropertyConsumptionKey electricityAverage(Integer electricityAverage) {
        this.electricityAverage = electricityAverage;
        return this;
    }

    public void setElectricityAverage(Integer electricityAverage) {
        this.electricityAverage = electricityAverage;
    }

    public Integer getGetAverage() {
        return getAverage;
    }

    public PropertyConsumptionKey getAverage(Integer getAverage) {
        this.getAverage = getAverage;
        return this;
    }

    public void setGetAverage(Integer getAverage) {
        this.getAverage = getAverage;
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
        PropertyConsumptionKey propertyConsumptionKey = (PropertyConsumptionKey) o;
        if (propertyConsumptionKey.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), propertyConsumptionKey.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PropertyConsumptionKey{" +
            "id=" + getId() +
            ", propertyType='" + getPropertyType() + "'" +
            ", numberOfRooms=" + getNumberOfRooms() +
            ", numberOfPeole=" + getNumberOfPeole() +
            ", electricityAverage=" + getElectricityAverage() +
            ", getAverage=" + getGetAverage() +
            "}";
    }
}
