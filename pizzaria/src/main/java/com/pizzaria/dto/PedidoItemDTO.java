package com.pizzaria.dto;

import java.math.BigDecimal;

import com.pizzaria.entity.Pedido;
import com.pizzaria.entity.PedidoItem.Tipo;

public class PedidoItemDTO {

    private Long id;
    private Pedido pedido;
    private Tipo tipo;
    private Long itemId;
    private Integer quantidade;
    private BigDecimal valorUnitario;

    public PedidoItemDTO() {
    }

    public PedidoItemDTO(Long id, Pedido pedido, Tipo tipo, Long itemId, Integer quantidade, BigDecimal valorUnitario) {
        this.id = id;
        this.pedido = pedido;
        this.tipo = tipo;
        this.itemId = itemId;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

}