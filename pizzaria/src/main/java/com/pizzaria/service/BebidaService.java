package com.pizzaria.service;

import java.math.BigDecimal;
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

    private static final String IMAGEM_PADRAO = "base";

    @Autowired
    private BebidaRepository repository;

    @Transactional(readOnly = true)
    public List<BebidaDTO> listar() {
        return BebidaMapper.toDTOList(repository.findAll());
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

        validarCadastro(dto);
        validarNomeUnico(dto.getNome(), id);

        existente.setNome(dto.getNome().trim());
        existente.setValor(dto.getValor());
        if (dto.getAtivo() != null) {
            existente.setAtivo(dto.getAtivo());
        }
        if (dto.getImagem() != null && !dto.getImagem().isBlank()) {
            existente.setImagem(resolverImagem(dto.getImagem()));
        }
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
        validarCadastro(dto);
        validarNomeUnico(dto.getNome(), null);
        Bebida bebida = BebidaMapper.toEntity(dto);
        bebida.setNome(dto.getNome().trim());
        bebida.setImagem(resolverImagem(dto.getImagem()));
        Bebida salvo = repository.save(bebida);
        return BebidaMapper.toDTO(salvo);
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

    private void validarCadastro(BebidaDTO dto) {
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
            throw new IllegalArgumentException("Já existe uma bebida com este nome.");
        }
    }
}
