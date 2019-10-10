package com.levi.manager.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordinateDTO {

    private Double latitude;
    private Double longitude;
    private Integer deliveryManId;

}
