package com.pizzaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pizzaria.entity.Cliente;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.usuario = :usuario AND c.ativo = true")
    boolean existsByUsuarioAtivo(@Param("usuario") String usuario);

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE REPLACE(REPLACE(c.cpf, '.', ''), '-', '') = :cpfNumeros AND c.ativo = true")
    boolean existsByCpfNumerosAtivo(@Param("cpfNumeros") String cpfNumeros);

    @Query("SELECT c FROM Cliente c WHERE c.usuario = :usuario AND c.ativo = true")
    Optional<Cliente> findAtivoByUsuario(@Param("usuario") String usuario);

    List<Cliente> findByUsuario(String usuario);
}
