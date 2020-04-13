package com.birkle.hatef.web.soap;

import com.birkle.hatef.domain.Vehicle;
import com.birkle.hatef.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SoapService {

    @Autowired
    VehicleRepository vehicleRepository;

    public void persist(List<VehicleDetails> vehicleDetails) {
        vehicleDetails.forEach(vd -> {
            Vehicle vehicle = new Vehicle();
            vehicle.setBrand(vd.getBrand());
            vehicle.setModel(vd.getModel());
            vehicle.setPlateCountry(vd.getPlateCountry());
            vehicle.setVehicleType(vd.getVehicleType());
            vehicle.setPlateNumber(vd.getPlateNumber());
            vehicle.setVin(vd.getVin());
            vehicle.setCreationDate(vd.getCreationDate());
            vehicle.setManufacturedCountry(vd.getManufacturedCountry());
            vehicleRepository.save(vehicle);
        });
    }
}
