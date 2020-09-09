package com.psss.registro.app.security;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

    Optional<UserAuthority> findByAuthority(String authority);
}
