package com.warehouse.administration.user.rolemapping;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping,Long>{
    Optional<UserRoleMapping> findByUserIdAndUserRoleId(Long userId,Long userRoleId);
    Optional<UserRoleMapping> findByUserIdAndUserRoleIdAndIdNot(Long userId,Long userRoleId,Long id);
}
