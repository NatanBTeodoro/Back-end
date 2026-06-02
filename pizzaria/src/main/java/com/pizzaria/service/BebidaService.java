package com.pizzaria.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.BebidaDTO;
import com.pizzaria.entity.Bebida;
import com.pizzaria.mapper.BebidaMapper;
import com.pizzaria.repository.BebidaRepository;

@Service
public class BebidaService {

    @Autowired
    private BebidaRepository repository;

    @Transactional(readOnly = true)
    public List<BebidaDTO> listar() {
        return BebidaMapper.toDTOList(repository.findAll()); // convertendo um mapper para um DTOList, converções
                                                             // são necessárias para os dados
    }

    @Transactional(readOnly = true)
    public BebidaDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(BebidaMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Bebida não encontrada com id: " + id));

    }

    @Transactional
    public BebidaDTO atualizar(Long id, BebidaDTO dto) {
        Bebida existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Bebida não encontrada com id: " + id));
        existente.setNome(dto.getNome());
        existente.setValor(dto.getValor());
        existente.setAtivo(dto.getAtivo());
        return BebidaMapper.toDTO(repository.save(existente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Bebida não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public BebidaDTO criar(BebidaDTO dto) {

        Bebida salvo = repository.save(BebidaMapper.toEntity(dto));
        return BebidaMapper.toDTO(salvo);
    }

}
