package com.ccs.core.repository;

import com.ccs.domain.entity.Pais;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
    Page<Pais> findByNomeContaining(String nome, Pageable pageable);
}
