package org.jai.kissan.repository;

import org.jai.kissan.model.FarmerFCIDeal;
import org.springframework.data.repository.CrudRepository;

public interface FarmerFCIRepository extends CrudRepository<FarmerFCIDeal,Long> {

    public FarmerFCIDeal findByDealIdentityCode(String dealIdentityCode);


}
