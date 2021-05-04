package org.jai.kissan.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FarmerFciController implements IFarmerFciController {

	private final FarmerFciService farmerFciService;

	@Override
	public Mono<FarmerFciDeal> createDeal(@RequestBody FarmerFciDeal farmerFciDeal) {
		return farmerFciService.createDeal(farmerFciDeal);
	}

	@Override
	public Mono<Void> buyDeal(@RequestParam String dealCode, @RequestParam Double buyingRate) {
		return farmerFciService.buyDeal(dealCode, buyingRate);
	}

	@Override
	public void updateQuantityInDeal(@RequestParam String dealCode, @RequestParam Double quantity) {
		farmerFciService.updateQuantityInDeal(dealCode, quantity);
	}

	@Override
	public Mono<Void> updateStatusToReview(@RequestParam String dealCode) {
		// ToDo : Send a notification to farmer
		return farmerFciService.updateDealStatusToReview(dealCode);
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
	public Flux<FarmerFciDeal> listFarmerActiveDeals(@PathVariable String farmerIdentityCode) {
		return farmerFciService.listFarmerActiveDeals(farmerIdentityCode);
	}

	@Override
	public Mono<Void> deleteAllFarmerFciDeals(String farmerIdentityCode) {
		return farmerFciService.deleteAllFarmerFciDeals(farmerIdentityCode);
	}

	@Override
	public Mono<Void> deleteFarmerFciDeal(String farmerFciDeal) {
		return farmerFciService.deleteFarmerFciDeal(farmerFciDeal);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerNewDeals(String farmerIdentityCode) {
		return farmerFciService.listFarmerNewDeals(farmerIdentityCode);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerReviewingDeals(String farmerIdentityCode) {
		return farmerFciService.listFarmerReviewingDeals(farmerIdentityCode);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerAllDeals(String farmerIdentityCode) {
		return farmerFciService.listFarmerAllDeals(farmerIdentityCode);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerCompletedDeals(String farmerIdentityCode) {
		// TODO Auto-generated method stub
		return null;
	}
}
