package com.ccs.core.repository;

import com.ccs.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token>findByToken(String apiKey);

    Optional <Token>findByLogin(String login);
}
