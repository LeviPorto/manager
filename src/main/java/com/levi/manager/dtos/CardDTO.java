package com.levi.manager.dtos;

import com.levi.manager.dtos.enuns.CardType;
import lombok.Data;

@Data
public class CardDTO {

    private CardType cardType;
    private String flag;
    private Boolean enabled;

}
