package com.unijorge.vendasMysql.repository;

import com.unijorge.vendasMysql.model.EstadoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository extends JpaRepository<EstadoModel, Long> {
}
