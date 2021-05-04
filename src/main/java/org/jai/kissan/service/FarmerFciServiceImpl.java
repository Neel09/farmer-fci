package org.jai.kissan.service;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

import org.jai.kissan.api.exception.ReadDataException;
import org.jai.kissan.api.exception.UpdateDataException;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.mapper.FarmerFciMapper;
import org.jai.kissan.persistence.entities.DealStatus;
import org.jai.kissan.persistence.entities.FarmerFciDealEntity;
import org.jai.kissan.persistence.repositories.FarmerFciRepository;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmerFciServiceImpl implements FarmerFciService {

	private final FarmerFciRepository farmerFciRepository;
	private final FarmerFciMapper farmerFciMapper;

	@Override
	public Mono<FarmerFciDeal> createDeal(FarmerFciDeal farmerFciDeal) {

		return Mono.defer(() -> {
			// ToDo: Get MSP rate from other service
			farmerFciDeal.setMspRate(100.0);
			farmerFciDeal.setDealCreatingDate(LocalDateTime.now());
			farmerFciDeal.setDealStatus(org.jai.kissan.api.farmer.fci.model.DealStatus.NEW);
			FarmerFciDealEntity dealEntity = farmerFciMapper.apiModelToEntityMapper(farmerFciDeal);

			return farmerFciRepository.save(dealEntity).map(farmerFciMapper::entityToApiModelMapper);
		});
	}

	@Override
	public Mono<Void> buyDeal(String dealCode, Double buyingRate) {

		return findDealWhichIsNotCompletedAndThenRunAnotherMono(dealCode, (entity) -> {
			// Setting values
			entity.setBuyingRate(buyingRate);
			entity.setDealStatus(DealStatus.COMPLETED);
			entity.setDealClosingDate(LocalDateTime.now());

			// Save data
			return farmerFciRepository.save(entity).onErrorMap(OptimisticLockingFailureException.class,
					error -> new UpdateDataException("ERROR: Data is stale. Please reload your data and try again..."));
		});
	}

	/**
	 * Bad Coding..... See updateDealStatusToReview() method for good practice...
	 * 
	 * Always let Spring try to subscribe Mono/Flux publisher, instead of explicitly
	 * subscribing to it.
	 * 
	 * In this, HTTP Response error code is always 200 (OK) even if there is an
	 * error... which misleads the user
	 * 
	 */
	@Override
	public void updateQuantityInDeal(String dealCode, final Double quantity) {

		findDealAndThenConsumeData(dealCode, (entity) -> {
			entity.setBuyingQuantity(quantity);
			entity.setDealLastUpdatingDate(LocalDateTime.now());
			// Save data
			farmerFciRepository.save(entity)
					.onErrorMap(OptimisticLockingFailureException.class, error -> new UpdateDataException(
							"Data is stale. Please reload your data and try again...ERROR:" + error.getMessage()))
					.subscribe(
							updatedEntity -> log
									.info("Deal updated successfully!! " + updatedEntity.getDealIdentityCode()),
							error -> log.error("Error while updating quantity in deal !! " + error.getMessage()),
							() -> log.info("Completed Save Task!"));
		});
	}

	/**
	 * Good practice,
	 *
	 * In this, HttpResponse Code is 200(ok) if all went good else return an error
	 *
	 */
	@Override
	public Mono<Void> updateDealStatusToReview(String dealCode) {

		return findDealAndThenRunAnotherMono(dealCode, (entity) -> {
			entity.setDealStatus(DealStatus.REVIEWING);
			// Save data
			return farmerFciRepository.save(entity);
		});
	}

	@Override
	public Flux<FarmerFciDeal> listAllNewDeals() {
		return farmerFciRepository.findByDealStatus(DealStatus.NEW).map(farmerFciMapper::entityToApiModelMapper);
	}

	@Override
	public Flux<FarmerFciDeal> listAllReviewingDeals() {
		return farmerFciRepository.findByDealStatus(DealStatus.REVIEWING).map(farmerFciMapper::entityToApiModelMapper);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerActiveDeals(String farmerIdentityCode) {

		// See difference between merge/concat in test example
		return Flux.merge(farmerFciRepository.findByDealStatusAndFarmerIdentityCode(DealStatus.NEW, farmerIdentityCode),
				farmerFciRepository.findByDealStatusAndFarmerIdentityCode(DealStatus.REVIEWING, farmerIdentityCode))
				.map(farmerFciMapper::entityToApiModelMapper);
	}

	@Override
	public Mono<Void> deleteAllFarmerFciDeals(String farmerIdentityCode) {
		return farmerFciRepository.deleteByFarmerIdentityCode(farmerIdentityCode);
	}

	@Override
	public Mono<Void> deleteFarmerFciDeal(String farmerFciDeal) {
		return farmerFciRepository.deleteByDealIdentityCode(farmerFciDeal);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerNewDeals(String farmerIdentityCode) {
		return farmerFciRepository.findByDealStatusAndFarmerIdentityCode(DealStatus.NEW, farmerIdentityCode)
				.map(farmerFciMapper::entityToApiModelMapper);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerReviewingDeals(String farmerIdentityCode) {
		return farmerFciRepository.findByDealStatusAndFarmerIdentityCode(DealStatus.REVIEWING, farmerIdentityCode)
				.map(farmerFciMapper::entityToApiModelMapper);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerCompletedDeals(String farmerIdentityCode) {
		return farmerFciRepository.findByDealStatusAndFarmerIdentityCode(DealStatus.COMPLETED, farmerIdentityCode)
				.map(farmerFciMapper::entityToApiModelMapper);
	}

	@Override
	public Flux<FarmerFciDeal> listFarmerAllDeals(String farmerIdentityCode) {
		return farmerFciRepository.findByFarmerIdentityCode(farmerIdentityCode)
				.map(farmerFciMapper::entityToApiModelMapper);
	}

	private void findDealAndThenConsumeData(@NonNull String dealCode, @NonNull Consumer<FarmerFciDealEntity> consumer) {

		Mono<FarmerFciDealEntity> farmerFciDealEntity = farmerFciRepository.findByDealIdentityCode(dealCode)
				.switchIfEmpty(Mono.error(new ReadDataException("Deal Code not found!!! : " + dealCode)));

		farmerFciDealEntity.subscribe(consumer,
				error -> log.error("Error while finding deal : {} : ERROR --> {}", dealCode, error.getMessage()),
				() -> log.info("Completed Find Deal Task! --> " + Thread.currentThread().getName()));
	}

	private Mono<Void> findDealAndThenRunAnotherMono(@NonNull String dealCode,
			@NonNull Function<FarmerFciDealEntity, Mono<FarmerFciDealEntity>> function) {

		Mono<FarmerFciDealEntity> farmerFciDealEntity = farmerFciRepository.findByDealIdentityCode(dealCode)
				.switchIfEmpty(Mono.error(new ReadDataException("Deal Code not found!!! : " + dealCode)));

		// function in the flatmap doesn't require to be subscribed explicitly
		return farmerFciDealEntity.flatMap(function).onErrorMap(OptimisticLockingFailureException.class,
				error -> new UpdateDataException("ERROR: Data is stale. Please reload your data and try again..."))
				.then();
	}

	private Mono<Void> findDealWhichIsNotCompletedAndThenRunAnotherMono(@NonNull String dealCode,
			@NonNull Function<FarmerFciDealEntity, Mono<FarmerFciDealEntity>> function) {

		Mono<FarmerFciDealEntity> farmerFciDealEntity = farmerFciRepository
				.findByDealIdentityCodeAndDealStatusNot(dealCode, DealStatus.COMPLETED)
				.switchIfEmpty(Mono.error(new ReadDataException(
						"Deal not found. Either it doesn't exist or already completed!! : " + dealCode)));

		return farmerFciDealEntity.flatMap(function).then();
	}

}
