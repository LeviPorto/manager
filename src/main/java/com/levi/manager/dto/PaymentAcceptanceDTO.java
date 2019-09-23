package com.levi.manager.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaymentAcceptanceDTO {

    private Boolean cash;
    private Boolean paycheck;
    private Boolean onlineTicket;
    private List<CardDTO> cardsDTOs;

}