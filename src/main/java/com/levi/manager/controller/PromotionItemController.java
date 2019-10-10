package com.levi.manager.controller;

import com.levi.manager.crud.AbstractCrudController;
import com.levi.manager.domain.PromotionItem;
import com.levi.manager.service.PromotionItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager/promotionItem")
public class PromotionItemController extends AbstractCrudController<PromotionItem> {

    public PromotionItemController(final PromotionItemService service) {
        super(service);
    }

}
