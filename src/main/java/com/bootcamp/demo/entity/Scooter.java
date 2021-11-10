package com.bootcamp.demo.entity;


import com.bootcamp.demo.enums.State;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Scooter {

    private String serial_number;
    private String brand_name;
    private BigDecimal acquisition_cost;
    private int production_year;
    private double weight;
    private State state;

}
