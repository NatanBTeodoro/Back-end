package com.pizzaria.dto;

import java.util.List;

import com.pizzaria.entity.PedidoItem.Tipo;

public class PedidoCriarDTO {

    private Long clienteId;
    private List<ItemCriarDTO> itens;

    public PedidoCriarDTO() {
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemCriarDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemCriarDTO> itens) {
        this.itens = itens;
    }

    public static class ItemCriarDTO {
        private Tipo tipo;
        private Long itemId;
        private Integer quantidade;

        public ItemCriarDTO() {
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
    }
}
