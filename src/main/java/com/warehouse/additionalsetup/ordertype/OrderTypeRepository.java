package com.warehouse.additionalsetup.ordertype;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {
    //Optional<OrderType> findByNameAndPrimaryCompanyId(String name, Long primaryCompanyId);

    //Optional<OrderType> findByNameAndPrimaryCompanyIdAndIdNot(String name, Long primaryCompanyId, Long id);

    Optional<OrderType> findByCodeAndPrimaryCompanyId(String code, Long primaryCompanyId);

    Optional<OrderType> findByCodeAndPrimaryCompanyIdAndIdNot(String code, Long primaryCompanyId, Long id);
}
