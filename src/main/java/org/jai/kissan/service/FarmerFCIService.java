package org.jai.kissan.service;

import org.jai.kissan.model.FarmerFCIDeal;

public interface FarmerFCIService {

    public String createDeal(FarmerFCIDeal farmerFCIDeal);

    public String buyDeal(String dealCode, Double buyingRate);

    public String updateQuantityInDeal(String dealCode, Double quantity);

    public String updateDealStatusToReview(String dealCode);
}
