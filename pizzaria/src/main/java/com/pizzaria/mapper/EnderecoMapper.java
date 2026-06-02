package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;


import com.pizzaria.dto.EnderecoDTO;
import com.pizzaria.entity.Endereco;

public class EnderecoMapper {

    public static EnderecoDTO toDTO(Endereco entity) {
        if (entity == null) {
            return null;
        }
        return new EnderecoDTO(entity.getId(), entity.getCep(),
                entity.getRotulo(), entity.getNomePersonalizado(), entity.getPrincipal());
    }

    public static Endereco toEntity(EnderecoDTO dto) {

        if (dto == null) {
            return null;
        }
        Endereco e = new Endereco();
        e.setCep(dto.getCep());
        e.setId(dto.getId());
        e.setNomePersonalizado(dto.getNomePersonalizado());
        e.setRotulo(dto.getRotulo());
        e.setPrincipal(dto.getPrincipal());
        return e;
    }

    public static List<EnderecoDTO> toDTOList(List<Endereco> list) {
        return list == null
                ? List.of()
                : list.stream().map(EnderecoMapper::toDTO).collect(Collectors.toList());

    }

    public static List<Endereco> toEntityList(List<EnderecoDTO> list) {
        return list == null
                ? List.of()
                : list.stream().map(EnderecoMapper::toEntity).collect(Collectors.toList());

    }
}
