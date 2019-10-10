package com.levi.manager.controller;

import com.levi.manager.crud.AbstractCrudController;
import com.levi.manager.domain.DeliveryMan;
import com.levi.manager.service.DeliveryManService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/deliveryMan")
public class DeliveryManController extends AbstractCrudController<DeliveryMan> {

    private final DeliveryManService service;

    public DeliveryManController(final DeliveryManService service) {
        super(service);
        this.service = service;
    }

    @GetMapping("restaurant/{restaurantId}/waitingAcceptance")
    public List<DeliveryMan> findByRestaurantAndWaitingAcceptance(@PathVariable Integer restaurantId) {
        return service.retrieveByRestaurantAndWaitingAcceptance(restaurantId);
    }

    @PutMapping("/{id}/acceptJob")
    public DeliveryMan acceptJob(@PathVariable Integer id) {
        return service.acceptJob(id);
    }


}
