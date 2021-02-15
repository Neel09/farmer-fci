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
	void buyDeal(String dealCode, Double buyingRate);

	void updateQuantityInDeal(String dealCode, Double quantity);

	void updateDealStatusToReview(String dealCode);

	Flux<FarmerFciDeal> listAllNewDeals();

	Flux<FarmerFciDeal> listAllReviewingDeals();

	Flux<FarmerFciDeal> listActiveDealsByFarmerCode(String farmerIdentityCode);

	void deleteAllFarmerFciDeals(String farmerIdentityCode);
}
