package com.pizzaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pizzaria.entity.Cliente;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByUsuario(String usuario);

    boolean existsByCpf(String cpf);

    List<Cliente> findByUsuario(String usuario);

    List<Cliente> findByCpf(String cpf);
}
