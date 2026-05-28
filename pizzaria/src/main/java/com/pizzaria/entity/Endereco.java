package com.pizzaria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(nullable = false, length = 9)
    private String cep;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Rotulo rotulo;

    @Column(name = "nome_personalizado", length = 100)
    private String nomePersonalizado; // obrigatório quando rotulo = OUTRO

    @Column(nullable = false)
    private Boolean principal = false;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    public Endereco() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}