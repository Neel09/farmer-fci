package org.jai.kissan.controller;

import lombok.RequiredArgsConstructor;
import org.jai.kissan.api.farmer.fci.controller.IFarmerFciController;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.service.FarmerFciService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class FarmerFciController implements IFarmerFciController {

    private final FarmerFciService farmerFciService;

    @Override
    public Mono<FarmerFciDeal> createDeal(@RequestBody FarmerFciDeal farmerFciDeal) {
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

        // ToDo : Send a notification to farmer
        farmerFciService.updateDealStatusToReview(dealCode);
    }

    @Override
    public Flux<FarmerFciDeal> listAllNewDeals() {
        return farmerFciService.listAllNewDeals();
    }

    @Override
    public Flux<FarmerFciDeal> listAllReviewingDeals() {
        return farmerFciService.listAllReviewingDeals();
    }

    @Override
    public Flux<FarmerFciDeal> getFciActiveDealFromFarmerCode(@PathVariable String farmerIdentityCode) {
        return farmerFciService.listActiveDealsByFarmerCode(farmerIdentityCode);
    }

    @Override
    public void deleteAllFarmerFciDeals(String farmerIdentityCode) {
        farmerFciService.deleteAllFarmerFciDeals(farmerIdentityCode);
    }

    @Override
    public void deleteFarmerFciDeal(String farmerFciDeal) {

    }

    @Override
    public Flux<FarmerFciDeal> listFarmerNewDeals(String farmerIdentityCode) {
        return null;
    }

    @Override
    public Flux<FarmerFciDeal> listAllReviewingDeals(String farmerIdentityCode) {
        return null;
    }

}
