package org.jai.kissan.service;

import java.util.List;

import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;

public interface FarmerFciService {

	/**
	 * @param farmerFciDeal
	 * @return fci deal code
	 */
	String createDeal(FarmerFciDeal farmerFciDeal);

	/**
	 * @param dealCode
	 * @param buyingRate
	 * @return acknowledgment
	 */
	String buyDeal(String dealCode, Double buyingRate);

	String updateQuantityInDeal(String dealCode, Double quantity);

	String updateDealStatusToReview(String dealCode);

	List<FarmerFciDeal> listAllNewDeals();

	List<FarmerFciDeal> listAllReviewingDeals();

	List<FarmerFciDeal> listActiveDealsByFarmerCode(String farmerIdentityCode);

	void deleteAllFarmerFciDeals(String farmerIdentityCode);
}
