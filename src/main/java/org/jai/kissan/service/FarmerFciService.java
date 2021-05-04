package org.jai.kissan.service;

import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FarmerFciService {

	/**
	 * @param farmerFciDeal
	 * @return fci deal code
	 */
	Mono<FarmerFciDeal> createDeal(FarmerFciDeal farmerFciDeal);

	/**
	 * @param dealCode
	 * @param buyingRate
	 * @return
	 */
	Mono<Void> buyDeal(String dealCode, Double buyingRate);

	void updateQuantityInDeal(String dealCode, Double quantity);

	Mono<Void> updateDealStatusToReview(String dealCode);

	Flux<FarmerFciDeal> listAllNewDeals();

	Flux<FarmerFciDeal> listAllReviewingDeals();

	Flux<FarmerFciDeal> listFarmerActiveDeals(String farmerIdentityCode);

	Flux<FarmerFciDeal> listFarmerNewDeals(String farmerIdentityCode);

	Flux<FarmerFciDeal> listFarmerReviewingDeals(String farmerIdentityCode);

	Flux<FarmerFciDeal> listFarmerCompletedDeals(String farmerIdentityCode);

	Flux<FarmerFciDeal> listFarmerAllDeals(String farmerIdentityCode);

	Mono<Void> deleteAllFarmerFciDeals(String farmerIdentityCode);

	Mono<Void> deleteFarmerFciDeal(String farmerFciDeal);
}
