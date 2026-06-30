package com.pizzaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.AdminLoginDTO;
import com.pizzaria.dto.DashboardDTO;
import com.pizzaria.dto.RecuperarSenhaDTO;
import com.pizzaria.entity.Admin;
import com.pizzaria.repository.AdminRepository;
import com.pizzaria.repository.ClienteRepository;
import com.pizzaria.util.SenhaUtil;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoService pedidoService;

    @Transactional(readOnly = true)
    public boolean login(AdminLoginDTO dto) {
        return adminRepository.findByUsuario(dto.getUsuario())
                .map(admin -> SenhaUtil.verificar(dto.getSenha(), admin.getSenha()))
                .orElse(false);
    }

    @Transactional
    public void recuperarSenha(RecuperarSenhaDTO dto) {
        if (dto.getUsuario() == null || dto.getUsuario().isBlank()) {
            throw new IllegalArgumentException("Usuário obrigatório.");
        }
        if (dto.getNovaSenha() == null || dto.getNovaSenha().length() < 6) {
            throw new IllegalArgumentException("Nova senha deve ter no mínimo 6 caracteres.");
        }

        Admin admin = adminRepository.findByUsuario(dto.getUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        admin.setSenha(SenhaUtil.criptografar(dto.getNovaSenha()));
        adminRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public DashboardDTO dashboard() {
        return new DashboardDTO(
                clienteRepository.count(),
                pedidoService.contarTotal(),
                pedidoService.contarHoje());
    }
}
