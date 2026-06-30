package com.pizzaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzaria.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByClienteIdOrderByPrincipalDescIdAsc(Long clienteId);

    long countByClienteId(Long clienteId);

    Optional<Endereco> findByIdAndClienteId(Long id, Long clienteId);

    Optional<Endereco> findFirstByClienteIdAndPrincipalTrue(Long clienteId);
}
