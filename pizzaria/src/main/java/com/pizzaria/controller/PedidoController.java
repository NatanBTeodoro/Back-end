package com.pizzaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizzaria.dto.PedidoConfirmacaoDTO;
import com.pizzaria.dto.PedidoCriarDTO;
import com.pizzaria.dto.PedidoListagemDTO;
import com.pizzaria.service.PedidoService;

@RestController
@RequestMapping("/pedidos")

public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoListagemDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoListagemDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<PedidoListagemDTO> criar(@RequestBody PedidoCriarDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @PutMapping("/{id}/confirmacao")
    public ResponseEntity<PedidoListagemDTO> confirmarRecebimento(
            @PathVariable Long id,
            @RequestBody PedidoConfirmacaoDTO dto) {
        return ResponseEntity.ok(service.confirmarRecebimento(id, dto));
    }
}
