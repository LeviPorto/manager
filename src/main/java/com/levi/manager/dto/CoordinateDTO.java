package com.levi.manager.dto;


import com.levi.manager.domain.parent.LocalizedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CoordinateDTO extends LocalizedEntity {

    private Double latitude;
    private Double longitude;
    private Integer deliveryManId;

}
