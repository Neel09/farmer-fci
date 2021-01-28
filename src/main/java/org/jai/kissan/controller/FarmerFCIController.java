package org.jai.kissan.controller;

import org.jai.kissan.model.FarmerFCIDeal;
import org.jai.kissan.service.FarmerFCIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("farmer-fci")
public class FarmerFCIController {

    @Autowired
    private FarmerFCIService farmerFCIService;

    @PostMapping("create-deal")
    public void createDeal(@RequestBody FarmerFCIDeal farmerFCIDeal){
        farmerFCIService.createDeal(farmerFCIDeal);
    }

    @PostMapping("buy-deal")
    public void buyDeal(@RequestParam String dealCode, @RequestParam Double buyingRate){
        farmerFCIService.buyDeal(dealCode,buyingRate);
    }

    @PostMapping("update-quantity")
    public void updateQuantityInDeal(@RequestParam String dealCode, @RequestParam Double quantity){
        farmerFCIService.updateQuantityInDeal(dealCode,quantity);
    }

}
