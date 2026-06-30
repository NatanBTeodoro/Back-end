package com.pizzaria.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pizzaria.dto.LogInteracaoDTO;
import com.pizzaria.entity.LogAlteracao;

public class LogInteracaoMapper {

    public static LogInteracaoDTO toDTO(LogAlteracao entity) {
        if (entity == null) {
            return null;
        }
        return new LogInteracaoDTO(entity.getId(), entity.getCampoAlterado(), entity.getDataHora());
    }

    public static List<LogInteracaoDTO> toDTOList(List<LogAlteracao> list) {
        return list == null
                ? List.of()
                : list.stream().map(LogInteracaoMapper::toDTO).collect(Collectors.toList());

    }
}
