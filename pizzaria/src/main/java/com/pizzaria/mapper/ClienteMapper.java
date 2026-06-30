package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pizzaria.dto.ClienteDTO;
import com.pizzaria.entity.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente entity) {
        if (entity == null) {
            return null;
        }
        return new ClienteDTO(
                entity.getId(),
                entity.getNomeCompleto(),
                entity.getCpf(),
                entity.getUsuario(),
                null,
                entity.getDataCadastro(),
                entity.getAtivo(),
                entity.getDataExclusao());
    }

    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente c = new Cliente();
        c.setCpf(dto.getCpf());
        c.setId(dto.getId());
        c.setNomeCompleto(dto.getNomeCompleto());
        c.setUsuario(dto.getUsuario());
        c.setSenha(dto.getSenha());
        c.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);
        return c;
    }

    public static List<ClienteDTO> toDTOList(List<Cliente> list) {
        return list == null
                ? List.of()
                : list.stream().map(ClienteMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Cliente> toEntityList(List<ClienteDTO> list) {
        return list == null
                ? List.of()
                : list.stream().map(ClienteMapper::toEntity).collect(Collectors.toList());
    }
}
