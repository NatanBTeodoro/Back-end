package com.pizzaria.service;

import java.math.BigDecimal;
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

    private static final String IMAGEM_PADRAO = "base";

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
    public PizzaDTO atualizar(Long id, PizzaDTO dto) {
        Pizza existente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pizza não encontrada com id: " + id));

        validarCadastro(dto);
        validarNomeUnico(dto.getNome(), id);

        existente.setNome(dto.getNome().trim());
        existente.setValor(dto.getValor());
        existente.setDescricao(dto.getDescricao() != null && !dto.getDescricao().isBlank()
                ? dto.getDescricao().trim()
                : "Sem descrição");
        if (dto.getAtivo() != null) {
            existente.setAtivo(dto.getAtivo());
        }
        if (dto.getImagem() != null && !dto.getImagem().isBlank()) {
            existente.setImagem(resolverImagem(dto.getImagem()));
        }
        return PizzaMapper.toDTO(repository.save(existente));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Pizza não encontrada com id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public PizzaDTO criar(PizzaDTO dto) {
        validarCadastro(dto);
        validarNomeUnico(dto.getNome(), null);
        Pizza pizza = PizzaMapper.toEntity(dto);
        pizza.setNome(dto.getNome().trim());
        pizza.setDescricao(dto.getDescricao() != null && !dto.getDescricao().isBlank()
                ? dto.getDescricao().trim()
                : "Sem descrição");
        pizza.setImagem(resolverImagem(dto.getImagem()));
        Pizza salvo = repository.save(pizza);
        return PizzaMapper.toDTO(salvo);
    }

    private String resolverImagem(String imagem) {
        if (imagem == null || imagem.isBlank()) {
            return IMAGEM_PADRAO;
        }
        String valor = imagem.trim();
        if (valor.startsWith("data:image/")) {
            return valor;
        }
        return valor;
    }

    private void validarCadastro(PizzaDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }
        if (dto.getValor() == null || dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor obrigatório.");
        }
    }

    private void validarNomeUnico(String nome, Long idIgnorado) {
        String nomeTrim = nome.trim();
        boolean duplicado = idIgnorado == null
                ? repository.existsByNome(nomeTrim)
                : repository.existsByNomeAndIdNot(nomeTrim, idIgnorado);
        if (duplicado) {
            throw new IllegalArgumentException("Já existe uma pizza com este nome.");
        }
    }

}
