package com.pizzaria.repository;

import com.pizzaria.entity.LogAlteracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogAlteracaoRepository extends JpaRepository<LogAlteracao, Long> {

  
    List<LogAlteracao> findByClienteIdOrderByDataHoraDesc(Long clienteId);

    
    @Query("SELECT l FROM LogAlteracao l INNER JOIN l.cliente c WHERE c.id = :clienteId ORDER BY l.dataHora DESC")
    List<LogAlteracao> buscarLogsComCliente(@Param("clienteId") Long clienteId);

}
