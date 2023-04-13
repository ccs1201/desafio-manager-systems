package com.ccs.core.repository;

import com.ccs.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TokenRepository extends JpaRepository<BigInteger, Token> {
}
