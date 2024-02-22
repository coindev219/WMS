package com.warehouse.additionalsetup.costbucket;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostBucketRepository extends JpaRepository<CostBucket, Long> {
    Optional<CostBucket> findByName(String name);
    Optional<CostBucket> findByNameAndIdNot(String name,Long id);
    Optional<CostBucket> findByCode(String code);
    Optional<CostBucket> findByCodeAndIdNot(String code,Long id);
}
