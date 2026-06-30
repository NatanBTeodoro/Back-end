package com.pizzaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.entity.Cliente;
import com.pizzaria.entity.LogAlteracao;
import com.pizzaria.repository.LogAlteracaoRepository;

@Service
public class LogAlteracaoService {

    @Autowired
    private LogAlteracaoRepository repository;

    @Transactional
    public void registrar(Cliente cliente, String campoAlterado) {
        LogAlteracao log = new LogAlteracao();
        log.setCliente(cliente);
        log.setCampoAlterado(campoAlterado);
        repository.save(log);
    }
}
