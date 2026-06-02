package com.pizzaria.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.ClienteDTO;
import com.pizzaria.entity.Cliente;
import com.pizzaria.mapper.ClienteMapper;
import com.pizzaria.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Transactional(readOnly = true)
    public List<ClienteDTO> listar() {
        return ClienteMapper.toDTOList(repository.findAll()); // convertendo um mapper para um DTOList, converções
                                                              // são necessárias para os dados
    }

    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(ClienteMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + id));

    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO dto) {
        Cliente existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + id));
        existente.setCpf(dto.getCpf());
        existente.setNomeCompleto(dto.getNomeCompleto());
        existente.setSenha(dto.getSenha());
        existente.setUsuario(dto.getUsuario());
        return ClienteMapper.toDTO(repository.save(existente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Cliente não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public ClienteDTO criar(ClienteDTO dto) {

        Cliente salvo = repository.save(ClienteMapper.toEntity(dto));
        return ClienteMapper.toDTO(salvo);
    }

    // Rever a questão de endereço dentro do cliente
}
