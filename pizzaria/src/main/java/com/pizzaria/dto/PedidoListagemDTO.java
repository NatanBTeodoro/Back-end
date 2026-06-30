package com.pizzaria.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoListagemDTO {

    private Long id;
    private String clienteNome;
    private Boolean clienteAtivo;
    private LocalDateTime dataHora;
    private BigDecimal valorTotal;
    private List<ItemListagemDTO> itens;
    private String status;
    private String enderecoEntrega;
    private String confirmacaoRecebimento;
    private String reclamacao;

    public PedidoListagemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Boolean getClienteAtivo() {
        return clienteAtivo;
    }

    public void setClienteAtivo(Boolean clienteAtivo) {
        this.clienteAtivo = clienteAtivo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<ItemListagemDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemListagemDTO> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getConfirmacaoRecebimento() {
        return confirmacaoRecebimento;
    }

    public void setConfirmacaoRecebimento(String confirmacaoRecebimento) {
        this.confirmacaoRecebimento = confirmacaoRecebimento;
    }

    public String getReclamacao() {
        return reclamacao;
    }

    public void setReclamacao(String reclamacao) {
        this.reclamacao = reclamacao;
    }

    public static class ItemListagemDTO {
        private Long id;
        private String nomeItem;
        private Integer quantidade;
        private BigDecimal valorUnitario;

        public ItemListagemDTO() {
        }

        public ItemListagemDTO(Long id, String nomeItem, Integer quantidade, BigDecimal valorUnitario) {
            this.id = id;
            this.nomeItem = nomeItem;
            this.quantidade = quantidade;
            this.valorUnitario = valorUnitario;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNomeItem() {
            return nomeItem;
        }

        public void setNomeItem(String nomeItem) {
            this.nomeItem = nomeItem;
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
}
