package com.chenyudaima.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 合格证表
 */
@Data
public class Certificate implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String brand;

    private String manufacturingCountry;

    private String vehicleType;

    private String vehicleIdentificationNumber;

    private String engineType;

    private String engineCapacity;

    private String maximumNetEnginePower;

    private String vintage;

    private String maximumAllowableTotalMass;

    private String numberOfPassengers;

    private String nameOfManufacturer;

    private String manufacturerSpecialNumber;


}