package com.warehouse.administration.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    
    Optional<UserRole> findByName(String name);
    Optional<UserRole> findByNameAndIdNot(String name,Long id);

    Optional<UserRole> findByCode(String code);
    Optional<UserRole> findByCodeAndIdNot(String code,Long id);
}
