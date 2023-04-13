package com.ccs.core.repository;

import com.ccs.domain.model.Pais;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    Page<Pais> findByNome(String nome, Pageable pageable);
}
