package com.pizzaria.verificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pizzaria.dto.ClienteDTO;
import com.pizzaria.repository.ClienteRepository;
import com.pizzaria.util.CpfUtil;

@Component
public class ClienteVerificacao {

    @Autowired
    private ClienteRepository repository;

    public void validarCadastro(ClienteDTO dto) {
        if (dto.getNomeCompleto() == null || dto.getNomeCompleto().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }
        if (dto.getUsuario() == null || dto.getUsuario().isBlank()) {
            throw new IllegalArgumentException("Usuário obrigatório.");
        }
        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF obrigatório.");
        }
        if (dto.getSenha() == null || dto.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres.");
        }

        String cpfNumeros = CpfUtil.normalizar(dto.getCpf());
        if (cpfNumeros.length() != 11) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (repository.existsByCpfNumerosAtivo(cpfNumeros)) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        if (repository.existsByUsuarioAtivo(dto.getUsuario())) {
            throw new IllegalArgumentException("Usuário já cadastrado.");
        }

        dto.setCpf(CpfUtil.formatar(cpfNumeros));
    }

    public void validarAtualizacao(Long id, ClienteDTO dto) {
        if (dto.getUsuario() != null && !dto.getUsuario().isBlank()) {
            repository.findById(id).ifPresent(cliente -> {
                if (!Boolean.TRUE.equals(cliente.getAtivo())) {
                    throw new IllegalArgumentException("Conta excluída.");
                }
                if (!cliente.getUsuario().equals(dto.getUsuario())
                        && repository.existsByUsuarioAtivo(dto.getUsuario())) {
                    throw new IllegalArgumentException("Usuário já cadastrado.");
                }
            });
        }
        if (dto.getSenha() != null && !dto.getSenha().isBlank() && dto.getSenha().length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres.");
        }
    }
}
