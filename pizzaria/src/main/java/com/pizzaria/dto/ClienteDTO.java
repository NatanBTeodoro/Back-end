package com.pizzaria.dto;

import java.time.LocalDateTime;

public class ClienteDTO {

    private Long id;
    private String nomeCompleto;
    private String cpf;
    private String usuario;
    private String senha;
    private LocalDateTime dataCadastro;
    private Boolean ativo;
    private LocalDateTime dataExclusao;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nomeCompleto, String cpf, String usuario, String senha,
            LocalDateTime dataCadastro, Boolean ativo, LocalDateTime dataExclusao) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.usuario = usuario;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
        this.dataExclusao = dataExclusao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }
}
