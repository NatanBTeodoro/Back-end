package com.pizzaria.dto;

import java.math.BigDecimal;

import com.pizzaria.entity.Cliente;

public class PedidoDTO {

    private Long id;
    private Cliente cliente;
    private BigDecimal valorTotal;

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, Cliente cliente, BigDecimal valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

}
