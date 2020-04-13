package com.birkle.hatef.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * The Vehicle entity.
 */
@ApiModel(description = "The Vehicle entity.")
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "plate_country")
    private String plateCountry;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "vin", unique = true)
    private String vin;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "manufactured_country")
    private String manufacturedCountry;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public Vehicle brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public Vehicle model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Vehicle vehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPlateCountry() {
        return plateCountry;
    }

    public Vehicle plateCountry(String plateCountry) {
        this.plateCountry = plateCountry;
        return this;
    }

    public void setPlateCountry(String plateCountry) {
        this.plateCountry = plateCountry;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Vehicle plateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVin() {
        return vin;
    }

    public Vehicle vin(String vin) {
        this.vin = vin;
        return this;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Vehicle creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getManufacturedCountry() {
        return manufacturedCountry;
    }

    public Vehicle manufacturedCountry(String manufacturedCountry) {
        this.manufacturedCountry = manufacturedCountry;
        return this;
    }

    public void setManufacturedCountry(String manufacturedCountry) {
        this.manufacturedCountry = manufacturedCountry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", vehicleType='" + getVehicleType() + "'" +
            ", plateCountry='" + getPlateCountry() + "'" +
            ", plateNumber='" + getPlateNumber() + "'" +
            ", vin='" + getVin() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", manufacturedCountry='" + getManufacturedCountry() + "'" +
            "}";
    }
}
