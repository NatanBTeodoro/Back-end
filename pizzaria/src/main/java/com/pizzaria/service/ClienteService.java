package com.pizzaria.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.ClienteDTO;
import com.pizzaria.dto.ClienteLoginDTO;
import com.pizzaria.dto.LogInteracaoDTO;
import com.pizzaria.dto.RecuperarSenhaDTO;
import com.pizzaria.entity.Cliente;
import com.pizzaria.mapper.ClienteMapper;
import com.pizzaria.mapper.LogInteracaoMapper;
import com.pizzaria.repository.ClienteRepository;
import com.pizzaria.repository.LogAlteracaoRepository;
import com.pizzaria.util.CpfUtil;
import com.pizzaria.util.SenhaUtil;
import com.pizzaria.verificacao.ClienteVerificacao;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteVerificacao verificacao;

    @Autowired
    private LogAlteracaoService logService;

    @Autowired
    private LogAlteracaoRepository logRepository;

    @Transactional(readOnly = true)
    public List<ClienteDTO> listar() {
        return ClienteMapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(ClienteMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + id));
    }

    @Transactional
    public ClienteDTO criar(ClienteDTO dto) {
        verificacao.validarCadastro(dto);
        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setSenha(SenhaUtil.criptografar(dto.getSenha()));
        cliente.setAtivo(true);
        Cliente salvo = repository.save(cliente);
        return ClienteMapper.toDTO(salvo);
    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO dto) {
        Cliente existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + id));

        garantirClienteAtivo(existente);
        verificacao.validarAtualizacao(id, dto);

        if (dto.getUsuario() != null && !dto.getUsuario().isBlank()
                && !dto.getUsuario().equals(existente.getUsuario())) {
            existente.setUsuario(dto.getUsuario());
            logService.registrar(existente, "usuario");
        }

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            existente.setSenha(SenhaUtil.criptografar(dto.getSenha()));
            logService.registrar(existente, "senha");
        }

        return ClienteMapper.toDTO(repository.save(existente));
    }

    @Transactional
    public void deletar(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + id));

        if (!Boolean.TRUE.equals(cliente.getAtivo())) {
            throw new IllegalArgumentException("Conta já está excluída.");
        }

        cliente.setAtivo(false);
        cliente.setDataExclusao(LocalDateTime.now());
        logService.registrar(cliente, "conta excluida");
        repository.save(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteDTO login(ClienteLoginDTO dto) {
        Cliente cliente = repository.findAtivoByUsuario(dto.getUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou senha inválidos."));

        if (!SenhaUtil.verificar(dto.getSenha(), cliente.getSenha())) {
            throw new IllegalArgumentException("Usuário ou senha inválidos.");
        }

        return ClienteMapper.toDTO(cliente);
    }

    @Transactional
    public void recuperarSenha(RecuperarSenhaDTO dto) {
        if (dto.getUsuario() == null || dto.getUsuario().isBlank()) {
            throw new IllegalArgumentException("Usuário obrigatório.");
        }
        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF obrigatório.");
        }
        if (dto.getNovaSenha() == null || dto.getNovaSenha().length() < 6) {
            throw new IllegalArgumentException("Nova senha deve ter no mínimo 6 caracteres.");
        }

        Cliente cliente = repository.findAtivoByUsuario(dto.getUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou CPF não conferem."));

        if (!CpfUtil.mesmoCpf(cliente.getCpf(), dto.getCpf())) {
            throw new IllegalArgumentException("Usuário ou CPF não conferem.");
        }

        cliente.setSenha(SenhaUtil.criptografar(dto.getNovaSenha()));
        repository.save(cliente);
        logService.registrar(cliente, "senha");
    }

    @Transactional(readOnly = true)
    public List<LogInteracaoDTO> listarLogs(Long clienteId) {
        if (!repository.existsById(clienteId)) {
            throw new NoSuchElementException("Cliente não encontrado com id: " + clienteId);
        }
        return LogInteracaoMapper.toDTOList(logRepository.findByClienteIdOrderByDataHoraDesc(clienteId));
    }

    public void garantirClienteAtivo(Cliente cliente) {
        if (!Boolean.TRUE.equals(cliente.getAtivo())) {
            throw new IllegalArgumentException("Conta excluída.");
        }
    }
}
