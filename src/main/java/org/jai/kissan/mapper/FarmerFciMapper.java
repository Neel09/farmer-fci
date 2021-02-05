package org.jai.kissan.mapper;

import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.persistence.entities.FarmerFciDealEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FarmerFciMapper {

    FarmerFciDeal entityToApiModelMapper(FarmerFciDealEntity farmerFciDealEntity);

    FarmerFciDealEntity apiModelToEntityMapper(FarmerFciDeal farmerFciDeal);

    List<FarmerFciDeal> entityToApiModelMapper(List<FarmerFciDealEntity> farmerFciDealEntities);

    List<FarmerFciDealEntity> apiModelToEntityMapper(List<FarmerFciDeal> farmerFciDeals);
}
