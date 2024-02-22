package com.warehouse.administration.user.costbucketmapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCostBucketMappingRepository extends JpaRepository<UserCostBucketMapping,Long>{
    Optional<UserCostBucketMapping> findByUserIdAndCostBucketId(Long userId,Long costBucketId);
    Optional<UserCostBucketMapping> findByUserIdAndCostBucketIdAndIdNot(Long userId,Long costBucketId,Long id);
}
