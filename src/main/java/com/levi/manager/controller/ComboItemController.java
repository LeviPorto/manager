package com.levi.manager.controller;

import com.levi.manager.crud.AbstractCrudController;
import com.levi.manager.domain.ComboItem;
import com.levi.manager.service.ComboItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/comboItem")
public class ComboItemController extends AbstractCrudController<ComboItem> {

    public ComboItemController(final ComboItemService service) {
        super(service);
    }

}
