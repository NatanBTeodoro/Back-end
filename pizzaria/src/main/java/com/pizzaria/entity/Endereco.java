package com.pizzaria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco {

    public enum Rotulo {
        CASA, TRABALHO, OUTRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(length = 9)
    private String cep;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Column(nullable = false, length = 200)
    private String rua;

    @Column(nullable = false, length = 20)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rotulo rotulo;

    @Column(name = "nome_personalizado", length = 100)
    private String nomePersonalizado;

    @Column(nullable = false)
    private Boolean principal = false;

    public Endereco() {
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Rotulo getRotulo() {
        return rotulo;
    }

    public void setRotulo(Rotulo rotulo) {
        this.rotulo = rotulo;
    }

    public String getNomePersonalizado() {
        return nomePersonalizado;
    }

    public void setNomePersonalizado(String nomePersonalizado) {
        this.nomePersonalizado = nomePersonalizado;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }
}
