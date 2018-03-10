package jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PropertyConsumptionKey.
 */
@Embeddable
public class PropertyConsumptionKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "number_of_rooms")
    private Integer numberOfRooms;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

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

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public PropertyConsumptionKey numberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
        return this;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyConsumptionKey that = (PropertyConsumptionKey) o;
        return Objects.equals(propertyType, that.propertyType) &&
            Objects.equals(numberOfRooms, that.numberOfRooms) &&
            Objects.equals(numberOfPeople, that.numberOfPeople);
    }

    @Override
    public int hashCode() {

        return Objects.hash(propertyType, numberOfRooms, numberOfPeople);
    }
}
