package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pizzaria.dto.BebidaDTO;
import com.pizzaria.entity.Bebida;

public class BebidaMapper {
    public static BebidaDTO toDTO(Bebida entity) {
        if (entity == null) {
            return null;
        }
        return new BebidaDTO(entity.getId(), entity.getNome(),
                entity.getValor(), entity.getAtivo(), entity.getImagem());
    }

    public static Bebida toEntity(BebidaDTO dto) {
        if (dto == null) {
            return null;
        }
        Bebida b = new Bebida();
        b.setId(dto.getId());
        b.setNome(dto.getNome());
        b.setValor(dto.getValor());
        b.setAtivo(dto.getAtivo());
        b.setImagem(dto.getImagem());
        return b;
    }

    public static List<BebidaDTO> toDTOList(List<Bebida> list) {
        return list == null
                ? List.of()
                : list.stream().map(BebidaMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Bebida> toEntityList(List<BebidaDTO> list) {
        return list == null
                ? List.of()
                : list.stream().map(BebidaMapper::toEntity).collect(Collectors.toList());
    }
}
