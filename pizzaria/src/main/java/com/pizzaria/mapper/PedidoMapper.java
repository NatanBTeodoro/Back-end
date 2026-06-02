package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pizzaria.dto.PedidoDTO;
import com.pizzaria.entity.Pedido;


public class PedidoMapper {
    public static PedidoDTO toDTO(Pedido entity) {
        if (entity == null) {
            return null;
        }
        return new PedidoDTO(entity.getId(), entity.getCliente(), entity.getValorTotal());
    }

    public static Pedido toEntity(PedidoDTO dto) {

        if (dto == null) {
            return null;
        }
        Pedido pe = new Pedido();
        pe.setId(dto.getId());
        pe.setCliente(dto.getCliente());
        pe.setValorTotal(dto.getValorTotal());
        return pe;
    }

    public static List<PedidoDTO> toDTOList(List<Pedido> list) {
        return list == null
                ? List.of()
                : list.stream().map(PedidoMapper::toDTO).collect(Collectors.toList());

    }

    public static List<Pedido> toEntityList(List<PedidoDTO> list) {
        return list == null
                ? List.of()
                : list.stream().map(PedidoMapper::toEntity).collect(Collectors.toList());

    }
}
