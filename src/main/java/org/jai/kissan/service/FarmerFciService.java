package org.jai.kissan.service;

import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;

import java.util.List;

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
}
