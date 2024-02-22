package com.warehouse.administration.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByUsernameAndPasswordAndIsActiveTrue(String username,String password);
    Optional<User> findByUsernameAndIsActiveTrue(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndIdNot(String username,Long id);
}
