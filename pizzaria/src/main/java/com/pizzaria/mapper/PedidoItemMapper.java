package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;


import com.pizzaria.dto.PedidoItemDTO;
import com.pizzaria.entity.PedidoItem;

public class PedidoItemMapper {

    public static PedidoItemDTO toDTO(PedidoItem entity) {
        if (entity == null) {
            return null;
        }
        return new PedidoItemDTO(entity.getId(), entity.getPedido(), entity.getTipo(), entity.getItemId(),
                entity.getQuantidade(), entity.getValorUnitario());
    }

    public static PedidoItem toEntity(PedidoItemDTO dto) {

        if (dto == null) {
            return null;
        }
        PedidoItem pi = new PedidoItem();
        pi.setId(dto.getId());
        pi.setItemId(dto.getItemId());
        pi.setPedido(dto.getPedido());
        pi.setQuantidade(dto.getQuantidade());
        pi.setTipo(dto.getTipo());
        pi.setValorUnitario(dto.getValorUnitario());
        return pi;
    }

    public static List<PedidoItemDTO> toDTOList(List<PedidoItem> list) {
        return list == null
                ? List.of()
                : list.stream().map(PedidoItemMapper::toDTO).collect(Collectors.toList());

    }

    public static List<PedidoItem> toEntityList(List<PedidoItemDTO> list) {
        return list == null
                ? List.of()
                : list.stream().map(PedidoItemMapper::toEntity).collect(Collectors.toList());

    }
}
