package com.pizzaria.dto;

import com.pizzaria.entity.Endereco.Rotulo;

public class EnderecoDTO {

    private Long id;
    private String cep;
    private Rotulo rotulo;
    private String nomePersonalizado;
    private Boolean principal;

    public EnderecoDTO() {
    }

    public EnderecoDTO(Long id, String cep, Rotulo rotulo, String nomePersonalizado, Boolean principal) {
        this.id = id;
        this.cep = cep;
        this.rotulo = rotulo;
        this.nomePersonalizado = nomePersonalizado;
        this.principal = principal;
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

}