package com.pizzaria.dto;

import java.time.LocalDateTime;

public class LogInteracaoDTO {

    private Long id;
    private String campoAlterado;
    private LocalDateTime dataHora;

    public LogInteracaoDTO() {
    }

    public LogInteracaoDTO(Long id, String campoAlterado, LocalDateTime dataHora) {
        this.id = id;
        this.campoAlterado = campoAlterado;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampoAlterado() {
        return campoAlterado;
    }

    public void setCampoAlterado(String campoAlterado) {
        this.campoAlterado = campoAlterado;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

}
