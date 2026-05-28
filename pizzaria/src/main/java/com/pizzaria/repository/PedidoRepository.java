package com.pizzaria.repository;

import com.pizzaria.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    
    List<Pedido> findByClienteIdOrderByDataHoraDesc(Long clienteId);

    
    List<Pedido> findAllByOrderByDataHoraDesc();

    
    int countByClienteId(Long clienteId);

   
    @Query("SELECT p FROM Pedido p INNER JOIN p.cliente c WHERE c.id = :clienteId ORDER BY p.dataHora DESC")
    List<Pedido> buscarPedidosComCliente(@Param("clienteId") Long clienteId);

}