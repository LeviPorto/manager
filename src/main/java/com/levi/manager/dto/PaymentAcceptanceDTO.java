package com.levi.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentAcceptanceDTO {

    private boolean cash;
    private boolean paycheck;
    private boolean onlineTicket;
    private List<CardDTO> cardsDTOs;

}
