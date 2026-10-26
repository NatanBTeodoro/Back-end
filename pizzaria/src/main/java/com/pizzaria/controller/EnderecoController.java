package com.pizzaria.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pizzaria.dto.EnderecoDTO;
import com.pizzaria.service.EnderecoService;

@RestController
@RequestMapping("/clientes/{clienteId}/enderecos")

public class EnderecoController {

    @Autowired
    private EnderecoService service;

    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> listar(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> criar(@PathVariable Long clienteId, @RequestBody EnderecoDTO dto) {
        EnderecoDTO criado = service.criar(clienteId, dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{enderecoId}")
    public ResponseEntity<EnderecoDTO> atualizar(
            @PathVariable Long clienteId,
            @PathVariable Long enderecoId,
            @RequestBody EnderecoDTO dto) {
        return ResponseEntity.ok(service.atualizar(clienteId, enderecoId, dto));
    }

    @DeleteMapping("/{enderecoId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId, @PathVariable Long enderecoId) {
        service.remover(clienteId, enderecoId);
        return ResponseEntity.noContent().build();
    }
}
