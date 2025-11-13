package com.unijorge.vendasMysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {
    private Long id;
    private String sigla;
    private String descricao;
}
