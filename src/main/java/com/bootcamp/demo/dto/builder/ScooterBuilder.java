package com.bootcamp.demo.dto.builder;

import com.bootcamp.demo.dto.ScooterDTO;
import com.bootcamp.demo.entity.Scooter;

import java.math.BigDecimal;

public class ScooterBuilder {

    private ScooterBuilder() {
    }

    public static Scooter toEntity(ScooterDTO scooterDTO) {
        return new Scooter(scooterDTO.getSerial_number(), scooterDTO.getBrand_name(), new BigDecimal(scooterDTO.getAcquisition_cost()),
                scooterDTO.getProduction_year(), scooterDTO.getWeight(), scooterDTO.getState());
    }
}
