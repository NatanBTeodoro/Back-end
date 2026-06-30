package com.pizzaria.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizzaria.dto.AdminLoginDTO;
import com.pizzaria.dto.DashboardDTO;
import com.pizzaria.dto.LogInteracaoDTO;
import com.pizzaria.dto.RecuperarSenhaDTO;
import com.pizzaria.service.AdminService;
import com.pizzaria.service.ClienteService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginDTO dto) {
        if (adminService.login(dto)) {
            return ResponseEntity.ok(Map.of("autenticado", true));
        }
        return ResponseEntity.status(401).body(Map.of("mensagem", "Usuário ou senha inválidos."));
    }

    @PostMapping("/recuperar-senha")
    public ResponseEntity<?> recuperarSenha(@RequestBody RecuperarSenhaDTO dto) {
        adminService.recuperarSenha(dto);
        return ResponseEntity.ok(Map.of("mensagem", "Senha redefinida com sucesso."));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> dashboard() {
        return ResponseEntity.ok(adminService.dashboard());
    }

    @GetMapping("/clientes/{id}/logs")
    public ResponseEntity<List<LogInteracaoDTO>> listarLogs(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.listarLogs(id));
    }
}
