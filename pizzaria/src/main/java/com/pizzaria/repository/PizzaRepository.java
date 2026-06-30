package com.pizzaria.repository;

import com.pizzaria.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {


    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, Long id);

    List<Pizza> findByAtivoTrue();

}