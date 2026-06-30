package com.pizzaria.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pizzaria.dto.BebidaDTO;
import com.pizzaria.service.BebidaService;

@RestController
@RequestMapping("/bebidas")
@CrossOrigin(origins = "http://localhost:3000")
public class BebidaController {

    @Autowired
    private BebidaService service;

    @GetMapping
    public ResponseEntity<List<BebidaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BebidaDTO> buscar(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<BebidaDTO> criar(@RequestBody BebidaDTO dto) {

        BebidaDTO criado = service.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/(id)").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(location).body(criado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<BebidaDTO> atualizar(@PathVariable Long id, @RequestBody BebidaDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        service.deletar(id);
        return ResponseEntity.noContent().build();

    }
}
