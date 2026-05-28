package com.pizzaria.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pizzaria.dto.PizzaDTO;
import com.pizzaria.entity.Pizza;
import com.pizzaria.mapper.PizzaMapper;
import com.pizzaria.repository.PizzaRepository;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository repository;

    @Transactional(readOnly = true)
    public List<PizzaDTO> listar() {
        return PizzaMapper.toDTOList(repository.findAll()); // convertendo um mapper para um DTOList, converções
                                                            // são necessárias para os dados
    }

    @Transactional(readOnly = true)
    public PizzaDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(PizzaMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Pizza não encontrada com id: " + id));

    }

    @Transactional
    public PizzaDTO criar(PizzaDTO dto) {

        Pizza salvo = repository.save(PizzaMapper.toEntity(dto));
        return PizzaMapper.toDTO(salvo);
    }

}
