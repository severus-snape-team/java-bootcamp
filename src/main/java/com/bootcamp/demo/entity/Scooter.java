package com.bootcamp.demo.entity;


import com.bootcamp.demo.enums.State;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Scooter {

    private String serial_number;
    private String brand_name;
    private double acquisition_cost;
    private int production_year;
    private double weight;
    private State state;

}
