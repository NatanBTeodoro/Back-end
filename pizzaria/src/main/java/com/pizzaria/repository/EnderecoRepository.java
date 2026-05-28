package com.pizzaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzaria.entity.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByClienteId(Long clienteId);

    Optional<Endereco> findByClienteIdAndPrincipalTrue(Long clienteId);

    int countByClienteId(Long clienteId);
}
