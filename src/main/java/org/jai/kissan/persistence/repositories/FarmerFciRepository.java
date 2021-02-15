package org.jai.kissan.persistence.repositories;

import org.jai.kissan.persistence.entities.DealStatus;
import org.jai.kissan.persistence.entities.FarmerFciDealEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FarmerFciRepository extends ReactiveMongoRepository<FarmerFciDealEntity, String> {

	Mono<FarmerFciDealEntity> findByDealIdentityCode(String dealIdentityCode);

	Flux<FarmerFciDealEntity> findByDealStatus(DealStatus dealStatus);

	Flux<FarmerFciDealEntity> findByDealStatusAndFarmerIdentityCode(DealStatus dealStatus, String farmerIdentityCode);

	Flux<FarmerFciDealEntity> deleteByFarmerIdentityCode(String farmerIdentityCode);
}
