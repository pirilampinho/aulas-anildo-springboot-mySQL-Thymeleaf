package com.unijorge.vendasMysql.repository;

import com.unijorge.vendasMysql.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
}
