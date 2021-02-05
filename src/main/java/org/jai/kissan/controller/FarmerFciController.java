package org.jai.kissan.controller;

import org.jai.kissan.api.farmer.fci.controller.IFarmerFciController;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.service.FarmerFciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FarmerFciController implements IFarmerFciController {

    private FarmerFciService farmerFciService;

    @Autowired
    public FarmerFciController(FarmerFciService farmerFciService) {
        this.farmerFciService = farmerFciService;
    }

    @Override
    public String createDeal(@RequestBody FarmerFciDeal farmerFciDeal) {
        return farmerFciService.createDeal(farmerFciDeal);
    }

    @Override
    public void buyDeal(@RequestParam String dealCode, @RequestParam Double buyingRate) {
        farmerFciService.buyDeal(dealCode, buyingRate);
    }

    @Override
    public void updateQuantityInDeal(@RequestParam String dealCode, @RequestParam Double quantity) {
        farmerFciService.updateQuantityInDeal(dealCode, quantity);
    }

    @Override
    public void updateStatusToReview(@RequestParam String dealCode) {

        //ToDo : Send a notification to farmer
        farmerFciService.updateDealStatusToReview(dealCode);
    }

    @Override
    public List<FarmerFciDeal> listAllNewDeals() {
        return farmerFciService.listAllNewDeals();
    }

    @Override
    public List<FarmerFciDeal> listAllReviewingDeals() {
        return farmerFciService.listAllReviewingDeals();
    }

    @Override
    public List<FarmerFciDeal> getFCIActiveDealFromFarmerCode(@PathVariable String farmerIdentityCode) {
        return farmerFciService.listActiveDealsByFarmerCode(farmerIdentityCode);
    }
}
