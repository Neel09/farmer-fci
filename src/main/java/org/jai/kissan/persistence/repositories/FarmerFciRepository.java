package org.jai.kissan.persistence.repositories;

import org.jai.kissan.persistence.entities.DealStatus;
import org.jai.kissan.persistence.entities.FarmerFciDealEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FarmerFciRepository extends CrudRepository<FarmerFciDealEntity, String> {

    FarmerFciDealEntity findByDealIdentityCode(String dealIdentityCode);

    List<FarmerFciDealEntity> findByDealStatus(DealStatus dealStatus);

    List<FarmerFciDealEntity> findByDealStatusAndFarmerIdentityCode(DealStatus dealStatus, String dealIdentityCode);

}
