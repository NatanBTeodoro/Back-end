package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pizzaria.dto.PizzaDTO;
import com.pizzaria.entity.Pizza;

public class PizzaMapper {
    public static PizzaDTO toDTO(Pizza entity) {

        if (entity == null) {
            return null;
        }
        return new PizzaDTO(entity.getId(), entity.getNome(), entity.getDescricao(), entity.getValor(),
                entity.getAtivo());

    }

    public static Pizza toEntity(PizzaDTO dto) {

        if (dto == null) {
            return null;
        }
        Pizza p = new Pizza();
        p.setId(dto.getId());
        p.setNome(dto.getNome());
        p.setValor(dto.getValor());
        p.setDescricao(dto.getDescricao());
        p.setAtivo(dto.getAtivo());
        return p;
    }

    public static List<PizzaDTO> toDTOList(List<Pizza> list) {
        return list == null
                ? List.of()
                : list.stream().map(PizzaMapper::toDTO).collect(Collectors.toList());

    }

    public static List<Pizza> toEntityList(List<PizzaDTO> list) {
        return list == null
                ? List.of()
                : list.stream().map(PizzaMapper::toEntity).collect(Collectors.toList());

    }
}
