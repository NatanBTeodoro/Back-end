package com.pizzaria.dto;

import java.math.BigDecimal;

public class BebidaDTO {

    private Long id;
    private String nome;
    private BigDecimal valor;
    private Boolean ativo;
    private String imagem;

    public BebidaDTO() {
    }

    public BebidaDTO(Long id, String nome, BigDecimal valor, Boolean ativo, String imagem) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.ativo = ativo;
        this.imagem = imagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
