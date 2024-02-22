package com.warehouse.setup.company.primarycompany;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryCompanyRepository extends JpaRepository<PrimaryCompany,Long>{
    Optional<PrimaryCompany> findByName(String name);
    Optional<PrimaryCompany> findByNameAndIdNot(String name,Long id);

    Optional<PrimaryCompany> findByCode(String code);
    Optional<PrimaryCompany> findByCodeAndIdNot(String code,Long id);

    Optional<PrimaryCompany> findByContactName(String contactName);
    Optional<PrimaryCompany> findByContactNameAndIdNot(String contactName,Long id);

}
