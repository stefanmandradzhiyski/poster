package com.snmi.repository;

import com.snmi.model.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtUserRepository extends JpaRepository<JwtUser, String> {

    Optional<JwtUser> findByUsername(final String username);

}
