package com.ervingorospe.grab_token.repository;

import com.ervingorospe.grab_token.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepo extends JpaRepository<Token, UUID> {
    Optional<Token> findByToken(String token);
}
