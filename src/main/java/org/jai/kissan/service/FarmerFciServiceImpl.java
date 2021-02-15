package org.jai.kissan.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.mapper.FarmerFciMapper;
import org.jai.kissan.persistence.entities.DealStatus;
import org.jai.kissan.persistence.entities.FarmerFciDealEntity;
import org.jai.kissan.persistence.repositories.FarmerFciRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.function.Consumer;

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
    public void buyDeal(String dealCode, Double buyingRate) {

        findDealAndThenConsumeData(dealCode, (entity) -> {
            // Setting values
            entity.setBuyingRate(buyingRate);
            entity.setDealStatus(DealStatus.COMPLETED);
            entity.setDealClosingDate(LocalDateTime.now());

            // Save data
            farmerFciRepository.save(entity).subscribe(
                    updatedEntity -> log.info("Deal updated successfully!! " + updatedEntity.getDealIdentityCode()),
                    error -> log.error("Error while buyingDeal!!" + error.getMessage()),
                    () -> log.info("Completed Save Task!"));
        });
    }

    @Override
    public void updateQuantityInDeal(String dealCode, final Double quantity) {

        findDealAndThenConsumeData(dealCode, (entity) -> {
            entity.setBuyingQuantity(quantity);
            entity.setDealLastUpdatingDate(LocalDateTime.now());
            // Save data
            farmerFciRepository.save(entity).log().subscribe(
                    updatedEntity -> log.info("Deal updated successfully!! " + updatedEntity.getDealIdentityCode()),
                    error -> log.error("Error while updating quantity in deal !! " + error.getMessage()),
                    () -> log.info("Completed Save Task!"));
        });
    }

    @Override
    public void updateDealStatusToReview(String dealCode) {
        Mono<FarmerFciDealEntity> farmerFciDealEntity = farmerFciRepository.findByDealIdentityCode(dealCode);

        findDealAndThenConsumeData(dealCode, (entity) -> {
            entity.setDealStatus(DealStatus.REVIEWING);
            // Save data
            farmerFciRepository.save(entity).subscribe(
                    updatedEntity -> log.info("Deal status updated successfully!! " + updatedEntity.getDealIdentityCode()),
                    error -> log.error("Error while updating status in deal !! " + error.getMessage()),
                    () -> log.info("Completed Save Task!"));
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
    public Flux<FarmerFciDeal> listActiveDealsByFarmerCode(String farmerIdentityCode) {

        //See difference between merge/concat in test example
        return Flux
                .merge(farmerFciRepository.findByDealStatusAndFarmerIdentityCode(DealStatus.NEW, farmerIdentityCode),
                        farmerFciRepository.findByDealStatusAndFarmerIdentityCode(DealStatus.REVIEWING, farmerIdentityCode))
                .map(farmerFciMapper::entityToApiModelMapper);
    }

    @Override
    public void deleteAllFarmerFciDeals(String farmerIdentityCode) {
        farmerFciRepository.deleteByFarmerIdentityCode(farmerIdentityCode)
                .log(Thread.currentThread().toString())
                .subscribeOn(Schedulers.single())
                .log(Thread.currentThread().toString()).subscribe();
    }

    private void findDealAndThenConsumeData(@NonNull String dealCode, @NonNull Consumer<FarmerFciDealEntity> consumer) {

        Mono<FarmerFciDealEntity> farmerFciDealEntity = farmerFciRepository.findByDealIdentityCode(dealCode)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Deal Code not found!!! : " + dealCode)));
        farmerFciDealEntity.subscribe(
                consumer,
                error -> log.error("Error while finding deal : {} : ERROR --> {}", dealCode, error.getMessage()),
                () -> log.info("Completed Find Deal Task!"));
    }
}
