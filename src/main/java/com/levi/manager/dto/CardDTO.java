package com.levi.manager.dto;

import com.levi.manager.dto.enumeration.CardType;
import lombok.Data;

@Data
public class CardDTO {

    private CardType cardType;
    private String flag;
    private Boolean enabled;

}
