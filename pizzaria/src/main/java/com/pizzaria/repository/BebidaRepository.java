package com.pizzaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzaria.entity.Bebida;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {
    
    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, Long id);

    List<Bebida> findByAtivo(Boolean ativo);
}
