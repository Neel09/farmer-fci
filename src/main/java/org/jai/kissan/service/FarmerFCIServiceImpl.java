package org.jai.kissan.service;

import org.jai.kissan.model.DealStatus;
import org.jai.kissan.model.FarmerFCIDeal;
import org.jai.kissan.repository.FarmerFCIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FarmerFCIServiceImpl implements FarmerFCIService{

    @Autowired
    private FarmerFCIRepository farmerFCIRepository;

    @Override
    public String createDeal(FarmerFCIDeal farmerFCIDeal) {
        //ToDo: Generate DealCode
        //ToDo: Get MSP rate from other service
        farmerFCIDeal.setMspRate(100.0);
        farmerFCIDeal.setDealCreatingDate(LocalDateTime.now());
        farmerFCIDeal.setDealStatus(DealStatus.NEW);
        farmerFCIRepository.save(farmerFCIDeal);
        return "Done";
    }

    @Override
    public String buyDeal(String dealCode, Double buyingRate) {
        FarmerFCIDeal farmerFCIDeal = farmerFCIRepository.findByDealIdentityCode(dealCode);
        farmerFCIDeal.setBuyingRate(buyingRate);
        farmerFCIDeal.setDealStatus(DealStatus.COMPLETED);
        farmerFCIDeal.setDealClosingDate(LocalDateTime.now());
        farmerFCIRepository.save(farmerFCIDeal);
        return "Done";
    }

    @Override
    public String updateQuantityInDeal(String dealCode, Double quantity) {
        FarmerFCIDeal farmerFCIDeal = farmerFCIRepository.findByDealIdentityCode(dealCode);
        farmerFCIDeal.setBuyingQuantity(quantity);
        farmerFCIDeal.setDealLastUpdatingDate(LocalDateTime.now());
        farmerFCIRepository.save(farmerFCIDeal);
        return "Done";
    }

    @Override
    public String updateDealStatusToReview(String dealCode) {
        FarmerFCIDeal farmerFCIDeal = farmerFCIRepository.findByDealIdentityCode(dealCode);
        farmerFCIDeal.setDealStatus(DealStatus.REVIEWING);
        farmerFCIRepository.save(farmerFCIDeal);
        return "Done";
    }
}
