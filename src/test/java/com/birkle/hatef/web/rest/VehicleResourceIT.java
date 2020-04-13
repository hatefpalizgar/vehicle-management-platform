package com.birkle.hatef.web.rest;

import com.birkle.hatef.VehicleManagementPlatformApp;
import com.birkle.hatef.domain.Vehicle;
import com.birkle.hatef.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration tests for the {@link VehicleResource} REST controller.
 */
@SpringBootTest(classes = VehicleManagementPlatformApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VehicleResourceIT {

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PLATE_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_PLATE_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PLATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PLATE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_VIN = "AAAAAAAAAA";
    private static final String UPDATED_VIN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MANUFACTURED_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createEntity(EntityManager em) {
        return new Vehicle()
            .brand(DEFAULT_BRAND)
            .model(DEFAULT_MODEL)
            .vehicleType(DEFAULT_VEHICLE_TYPE)
            .plateCountry(DEFAULT_PLATE_COUNTRY)
            .plateNumber(DEFAULT_PLATE_NUMBER)
            .vin(DEFAULT_VIN)
            .creationDate(DEFAULT_CREATION_DATE)
            .manufacturedCountry(DEFAULT_MANUFACTURED_COUNTRY);
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        return new Vehicle()
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .vehicleType(UPDATED_VEHICLE_TYPE)
            .plateCountry(UPDATED_PLATE_COUNTRY)
            .plateNumber(UPDATED_PLATE_NUMBER)
            .vin(UPDATED_VIN)
            .creationDate(UPDATED_CREATION_DATE)
            .manufacturedCountry(UPDATED_MANUFACTURED_COUNTRY);
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();
        // Create the Vehicle
        restVehicleMockMvc.perform(post("/api/vehicles")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(TestUtil.convertObjectToJsonBytes(vehicle)))
                          .andExpect(status().isCreated());
        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getVehicleType()).isEqualTo(DEFAULT_VEHICLE_TYPE);
        assertThat(testVehicle.getPlateCountry()).isEqualTo(DEFAULT_PLATE_COUNTRY);
        assertThat(testVehicle.getPlateNumber()).isEqualTo(DEFAULT_PLATE_NUMBER);
        assertThat(testVehicle.getVin()).isEqualTo(DEFAULT_VIN);
        assertThat(testVehicle.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testVehicle.getManufacturedCountry()).isEqualTo(DEFAULT_MANUFACTURED_COUNTRY);
    }

    @Test
    @Transactional
    public void createVehicleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();
        // Create the Vehicle with an existing ID
        vehicle.setId(1L);
        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc.perform(post("/api/vehicles")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(TestUtil.convertObjectToJsonBytes(vehicle)))
                          .andExpect(status().isBadRequest());
        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        // Get all the vehicleList
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
                          .andExpect(status().isOk())
                          .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                          .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
                          .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
                          .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
                          .andExpect(jsonPath("$.[*].vehicleType").value(hasItem(DEFAULT_VEHICLE_TYPE)))
                          .andExpect(jsonPath("$.[*].plateCountry").value(hasItem(DEFAULT_PLATE_COUNTRY)))
                          .andExpect(jsonPath("$.[*].plateNumber").value(hasItem(DEFAULT_PLATE_NUMBER)))
                          .andExpect(jsonPath("$.[*].vin").value(hasItem(DEFAULT_VIN)))
                          .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
                          .andExpect(jsonPath("$.[*].manufacturedCountry").value(hasItem(DEFAULT_MANUFACTURED_COUNTRY)));
    }

    @Test
    @Transactional
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
                          .andExpect(status().isOk())
                          .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                          .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
                          .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
                          .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
                          .andExpect(jsonPath("$.vehicleType").value(DEFAULT_VEHICLE_TYPE))
                          .andExpect(jsonPath("$.plateCountry").value(DEFAULT_PLATE_COUNTRY))
                          .andExpect(jsonPath("$.plateNumber").value(DEFAULT_PLATE_NUMBER))
                          .andExpect(jsonPath("$.vin").value(DEFAULT_VIN))
                          .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
                          .andExpect(jsonPath("$.manufacturedCountry").value(DEFAULT_MANUFACTURED_COUNTRY));
    }

    @Test
    @Transactional
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
                          .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .brand(UPDATED_BRAND)
            .model(UPDATED_MODEL)
            .vehicleType(UPDATED_VEHICLE_TYPE)
            .plateCountry(UPDATED_PLATE_COUNTRY)
            .plateNumber(UPDATED_PLATE_NUMBER)
            .vin(UPDATED_VIN)
            .creationDate(UPDATED_CREATION_DATE)
            .manufacturedCountry(UPDATED_MANUFACTURED_COUNTRY);
        restVehicleMockMvc.perform(put("/api/vehicles")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(TestUtil.convertObjectToJsonBytes(updatedVehicle)))
                          .andExpect(status().isOk());
        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getVehicleType()).isEqualTo(UPDATED_VEHICLE_TYPE);
        assertThat(testVehicle.getPlateCountry()).isEqualTo(UPDATED_PLATE_COUNTRY);
        assertThat(testVehicle.getPlateNumber()).isEqualTo(UPDATED_PLATE_NUMBER);
        assertThat(testVehicle.getVin()).isEqualTo(UPDATED_VIN);
        assertThat(testVehicle.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testVehicle.getManufacturedCountry()).isEqualTo(UPDATED_MANUFACTURED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();
        // Create the Vehicle
        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc.perform(put("/api/vehicles")
                                       .contentType(MediaType.APPLICATION_JSON)
                                       .content(TestUtil.convertObjectToJsonBytes(vehicle)))
                          .andExpect(status().isBadRequest());
        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();
        // Delete the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
                                       .accept(MediaType.APPLICATION_JSON))
                          .andExpect(status().isNoContent());
        // Validate the database contains one less item
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
