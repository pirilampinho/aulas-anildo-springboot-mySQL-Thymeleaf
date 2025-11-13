package com.unijorge.vendasMysql.mapper;

import com.unijorge.vendasMysql.dto.EstadoDTO;
import com.unijorge.vendasMysql.model.EstadoModel;
import org.springframework.stereotype.Component;

@Component
public class EstadoMapper {
    public EstadoModel map(EstadoDTO estadoDTO) {
        EstadoModel estadoModel = new EstadoModel();
        estadoModel.setId(estadoDTO.getId());
        estadoModel.setSigla(estadoDTO.getSigla());
        estadoModel.setDescricao(estadoDTO.getDescricao());
        return estadoModel;
    }

    public EstadoDTO map(EstadoModel estadoModel) {
        EstadoDTO estadoDTO = new EstadoDTO();
        estadoDTO.setId(estadoModel.getId());
        estadoDTO.setSigla(estadoModel.getSigla());
        estadoDTO.setDescricao(estadoModel.getDescricao());
        return estadoDTO;
    }
}
