package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pizzaria.dto.EnderecoDTO;
import com.pizzaria.entity.Endereco;

public class EnderecoMapper {

    private EnderecoMapper() {
    }

    public static EnderecoDTO toDTO(Endereco entity) {
        if (entity == null) {
            return null;
        }
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId(entity.getId());
        dto.setClienteId(entity.getCliente() != null ? entity.getCliente().getId() : null);
        dto.setCep(entity.getCep());
        dto.setCidade(entity.getCidade());
        dto.setBairro(entity.getBairro());
        dto.setRua(entity.getRua());
        dto.setNumero(entity.getNumero());
        dto.setRotulo(entity.getRotulo() != null ? entity.getRotulo().name() : null);
        dto.setNomePersonalizado(entity.getNomePersonalizado());
        dto.setPrincipal(entity.getPrincipal());
        return dto;
    }

    public static Endereco toEntity(EnderecoDTO dto) {
        if (dto == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(dto.getId());
        endereco.setCep(dto.getCep());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        if (dto.getRotulo() != null) {
            endereco.setRotulo(Endereco.Rotulo.valueOf(dto.getRotulo()));
        }
        endereco.setNomePersonalizado(dto.getNomePersonalizado());
        endereco.setPrincipal(Boolean.TRUE.equals(dto.getPrincipal()));
        return endereco;
    }

    public static List<EnderecoDTO> toDTOList(List<Endereco> list) {
        return list == null
                ? List.of()
                : list.stream().map(EnderecoMapper::toDTO).collect(Collectors.toList());
    }
}
