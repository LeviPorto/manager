package com.levi.manager.dto;

import com.levi.manager.dto.enumeration.CardType;
import lombok.Data;

import java.util.Objects;

@Data
public class CardDTO {

    private CardType cardType;
    private String flag;
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return cardType == cardDTO.cardType && flag.equals(cardDTO.flag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardType, flag);
    }
}
