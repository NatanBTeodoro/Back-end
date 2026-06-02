package com.pizzaria.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.EnderecoDTO;
import com.pizzaria.entity.Endereco;
import com.pizzaria.mapper.EnderecoMapper;
import com.pizzaria.repository.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Transactional(readOnly = true)
    public List<EnderecoDTO> listar() {
        return EnderecoMapper.toDTOList(repository.findAll()); // convertendo um mapper para um DTOList, converções
                                                               // são necessárias para os dados
    }

    @Transactional(readOnly = true)
    public EnderecoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(EnderecoMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Endereco não encontrado com id: " + id));

    }

    @Transactional
    public EnderecoDTO atualizar(Long id, EnderecoDTO dto) {
        Endereco existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com id: " + id));
        existente.setNomePersonalizado(dto.getNomePersonalizado());
        existente.setCep(dto.getCep());
        existente.setRotulo(dto.getRotulo());
        existente.setPrincipal(dto.getPrincipal());

        return EnderecoMapper.toDTO(repository.save(existente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Endereco não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public EnderecoDTO criar(EnderecoDTO dto) {

        Endereco salvo = repository.save(EnderecoMapper.toEntity(dto));
        return EnderecoMapper.toDTO(salvo);
    }

}
