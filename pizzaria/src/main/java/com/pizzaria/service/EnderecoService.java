package com.pizzaria.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.EnderecoDTO;
import com.pizzaria.entity.Cliente;
import com.pizzaria.entity.Endereco;
import com.pizzaria.entity.Endereco.Rotulo;
import com.pizzaria.mapper.EnderecoMapper;
import com.pizzaria.repository.ClienteRepository;
import com.pizzaria.repository.EnderecoRepository;

@Service
public class EnderecoService {

    private static final int LIMITE_ENDERECOS = 7;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LogAlteracaoService logService;

    @Transactional(readOnly = true)
    public List<EnderecoDTO> listarPorCliente(Long clienteId) {
        validarClienteExiste(clienteId);
        return EnderecoMapper.toDTOList(enderecoRepository.findByClienteIdOrderByPrincipalDescIdAsc(clienteId));
    }

    @Transactional
    public EnderecoDTO criar(Long clienteId, EnderecoDTO dto) {
        Cliente cliente = buscarClienteAtivo(clienteId);
        validarEndereco(dto);

        if (enderecoRepository.countByClienteId(clienteId) >= LIMITE_ENDERECOS) {
            throw new IllegalArgumentException("Limite de 7 endereços atingido.");
        }

        Endereco endereco = EnderecoMapper.toEntity(dto);
        endereco.setCliente(cliente);
        endereco.setCep(endereco.getCep() != null && endereco.getCep().isBlank() ? null : endereco.getCep());

        boolean primeiroEndereco = enderecoRepository.countByClienteId(clienteId) == 0;
        if (primeiroEndereco || Boolean.TRUE.equals(dto.getPrincipal())) {
            desmarcarPrincipal(clienteId);
            endereco.setPrincipal(true);
        } else {
            endereco.setPrincipal(false);
        }

        Endereco salvo = enderecoRepository.save(endereco);
        logService.registrar(cliente, "endereco");
        return EnderecoMapper.toDTO(salvo);
    }

    @Transactional
    public EnderecoDTO atualizar(Long clienteId, Long enderecoId, EnderecoDTO dto) {
        Cliente cliente = buscarClienteAtivo(clienteId);
        Endereco existente = buscarEndereco(clienteId, enderecoId);
        validarEndereco(dto);

        existente.setCep(dto.getCep() != null && dto.getCep().isBlank() ? null : dto.getCep());
        existente.setCidade(dto.getCidade().trim());
        existente.setBairro(dto.getBairro().trim());
        existente.setRua(dto.getRua().trim());
        existente.setNumero(dto.getNumero().trim());
        existente.setRotulo(Rotulo.valueOf(dto.getRotulo()));
        existente.setNomePersonalizado(
                existente.getRotulo() == Rotulo.OUTRO ? dto.getNomePersonalizado().trim() : null);

        if (Boolean.TRUE.equals(dto.getPrincipal())) {
            desmarcarPrincipal(clienteId);
            existente.setPrincipal(true);
        }

        Endereco salvo = enderecoRepository.save(existente);
        logService.registrar(cliente, "endereco");
        return EnderecoMapper.toDTO(salvo);
    }

    @Transactional
    public void remover(Long clienteId, Long enderecoId) {
        Cliente cliente = buscarClienteAtivo(clienteId);
        Endereco existente = buscarEndereco(clienteId, enderecoId);
        boolean eraPrincipal = Boolean.TRUE.equals(existente.getPrincipal());

        enderecoRepository.delete(existente);

        if (eraPrincipal) {
            List<Endereco> restantes = enderecoRepository.findByClienteIdOrderByPrincipalDescIdAsc(clienteId);
            if (!restantes.isEmpty()) {
                Endereco novoPrincipal = restantes.get(0);
                novoPrincipal.setPrincipal(true);
                enderecoRepository.save(novoPrincipal);
            }
        }

        logService.registrar(cliente, "endereco");
    }

    @Transactional(readOnly = true)
    public Endereco buscarEnderecoEntrega(Long clienteId) {
        return enderecoRepository.findFirstByClienteIdAndPrincipalTrue(clienteId)
                .or(() -> enderecoRepository.findByClienteIdOrderByPrincipalDescIdAsc(clienteId).stream().findFirst())
                .orElse(null);
    }

    private void validarEndereco(EnderecoDTO dto) {
        if (dto.getRotulo() == null || dto.getRotulo().isBlank()) {
            throw new IllegalArgumentException("Tipo do endereço obrigatório.");
        }
        try {
            Rotulo.valueOf(dto.getRotulo());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Tipo de endereço inválido.");
        }
        if (Rotulo.OUTRO.name().equals(dto.getRotulo())
                && (dto.getNomePersonalizado() == null || dto.getNomePersonalizado().isBlank())) {
            throw new IllegalArgumentException("Informe o nome do endereço quando o tipo for Outro.");
        }
        if (dto.getCidade() == null || dto.getCidade().isBlank()) {
            throw new IllegalArgumentException("Cidade obrigatória.");
        }
        if (dto.getBairro() == null || dto.getBairro().isBlank()) {
            throw new IllegalArgumentException("Bairro obrigatório.");
        }
        if (dto.getRua() == null || dto.getRua().isBlank()) {
            throw new IllegalArgumentException("Rua obrigatória.");
        }
        if (dto.getNumero() == null || dto.getNumero().isBlank()) {
            throw new IllegalArgumentException("Número da casa obrigatório.");
        }
    }

    private void desmarcarPrincipal(Long clienteId) {
        enderecoRepository.findByClienteIdOrderByPrincipalDescIdAsc(clienteId).forEach(end -> {
            if (Boolean.TRUE.equals(end.getPrincipal())) {
                end.setPrincipal(false);
                enderecoRepository.save(end);
            }
        });
    }

    private Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + clienteId));
    }

    private Cliente buscarClienteAtivo(Long clienteId) {
        Cliente cliente = buscarCliente(clienteId);
        if (!Boolean.TRUE.equals(cliente.getAtivo())) {
            throw new IllegalArgumentException("Conta excluída.");
        }
        return cliente;
    }

    private void validarClienteExiste(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new NoSuchElementException("Cliente não encontrado com id: " + clienteId);
        }
    }

    private Endereco buscarEndereco(Long clienteId, Long enderecoId) {
        return enderecoRepository.findByIdAndClienteId(enderecoId, clienteId)
                .orElseThrow(() -> new NoSuchElementException("Endereço não encontrado com id: " + enderecoId));
    }
}
