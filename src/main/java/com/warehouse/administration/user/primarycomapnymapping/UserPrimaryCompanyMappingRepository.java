package com.warehouse.administration.user.primarycomapnymapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrimaryCompanyMappingRepository extends JpaRepository<UserPrimaryCompanyMapping,Long>{
    Optional<UserPrimaryCompanyMapping> findByUserIdAndPrimaryCompanyId(Long userId,Long primaryCompanyId);
    Optional<UserPrimaryCompanyMapping> findByUserIdAndPrimaryCompanyIdAndIdNot(Long userId,Long primaryCompanyId,Long id);
}
