package org.jai.kissan.service;

import java.time.LocalDateTime;
import java.util.List;

import org.jai.kissan.api.farmer.fci.model.DealStatus;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.mapper.FarmerFciMapper;
import org.jai.kissan.persistence.entities.FarmerFciDealEntity;
import org.jai.kissan.persistence.repositories.FarmerFciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmerFciServiceImpl implements FarmerFciService {

	private final FarmerFciRepository farmerFciRepository;
	private final FarmerFciMapper farmerFciMapper;

	@Autowired
	public FarmerFciServiceImpl(FarmerFciRepository farmerFciRepository, FarmerFciMapper farmerFciMapper) {
		this.farmerFciRepository = farmerFciRepository;
		this.farmerFciMapper = farmerFciMapper;
	}

	@Override
	public String createDeal(FarmerFciDeal farmerFciDeal) {
		// ToDo: Get MSP rate from other service
		farmerFciDeal.setMspRate(100.0);
		farmerFciDeal.setDealCreatingDate(LocalDateTime.now());
		farmerFciDeal.setDealStatus(DealStatus.NEW);
		FarmerFciDealEntity farmerFciDealEntity = farmerFciRepository
				.save(farmerFciMapper.apiModelToEntityMapper(farmerFciDeal));
		return farmerFciDealEntity.getDealIdentityCode();
	}

	@Override
	public String buyDeal(String dealCode, Double buyingRate) {
		FarmerFciDealEntity farmerFciDealEntity = farmerFciRepository.findByDealIdentityCode(dealCode);
		farmerFciDealEntity.setBuyingRate(buyingRate);
		farmerFciDealEntity.setDealStatus(org.jai.kissan.persistence.entities.DealStatus.COMPLETED);
		farmerFciDealEntity.setDealClosingDate(LocalDateTime.now());
		return farmerFciRepository.save(farmerFciDealEntity).getDealIdentityCode();
	}

	@Override
	public String updateQuantityInDeal(String dealCode, Double quantity) {
		FarmerFciDealEntity farmerFciDealEntity = farmerFciRepository.findByDealIdentityCode(dealCode);
		farmerFciDealEntity.setBuyingQuantity(quantity);
		farmerFciDealEntity.setDealLastUpdatingDate(LocalDateTime.now());
		farmerFciRepository.save(farmerFciDealEntity);
		return "Done";
	}

	@Override
	public String updateDealStatusToReview(String dealCode) {
		FarmerFciDealEntity farmerFciDealEntity = farmerFciRepository.findByDealIdentityCode(dealCode);
		farmerFciDealEntity.setDealStatus(org.jai.kissan.persistence.entities.DealStatus.REVIEWING);
		farmerFciRepository.save(farmerFciDealEntity);
		return "Done";
	}

	@Override
	public List<FarmerFciDeal> listAllNewDeals() {
		List<FarmerFciDealEntity> fciDealEntities = farmerFciRepository
				.findByDealStatus(org.jai.kissan.persistence.entities.DealStatus.NEW);
		return farmerFciMapper.entityToApiModelMapper(fciDealEntities);
	}

	@Override
	public List<FarmerFciDeal> listAllReviewingDeals() {
		List<FarmerFciDealEntity> fciDealEntities = farmerFciRepository
				.findByDealStatus(org.jai.kissan.persistence.entities.DealStatus.REVIEWING);
		return farmerFciMapper.entityToApiModelMapper(fciDealEntities);
	}

	@Override
	public List<FarmerFciDeal> listActiveDealsByFarmerCode(String farmerIdentityCode) {
		List<FarmerFciDealEntity> activeDeals = farmerFciRepository.findByDealStatusAndFarmerIdentityCode(
				org.jai.kissan.persistence.entities.DealStatus.NEW, farmerIdentityCode);
		activeDeals.addAll(farmerFciRepository.findByDealStatusAndFarmerIdentityCode(
				org.jai.kissan.persistence.entities.DealStatus.REVIEWING, farmerIdentityCode));
		return farmerFciMapper.entityToApiModelMapper(activeDeals);
	}

	@Override
	public void deleteAllFarmerFciDeals(String farmerIdentityCode) {
		farmerFciRepository.deleteByFarmerIdentityCode(farmerIdentityCode);
	}

}
