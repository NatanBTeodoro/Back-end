package com.pizzaria.dto;

import com.pizzaria.entity.Cliente;

public class LogInteracaoDTO {

    private Long id;
    private Cliente cliente;
    private String campoAlterado;

    public LogInteracaoDTO() {
    }

    public LogInteracaoDTO(Long id, Cliente cliente, String campoAlterado) {
        this.id = id;
        this.cliente = cliente;
        this.campoAlterado = campoAlterado;
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

    public String getCampoAlterado() {
        return campoAlterado;
    }

    public void setCampoAlterado(String campoAlterado) {
        this.campoAlterado = campoAlterado;
    }

}
