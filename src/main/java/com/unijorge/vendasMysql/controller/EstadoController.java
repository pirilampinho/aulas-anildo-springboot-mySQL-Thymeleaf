package com.unijorge.vendasMysql.controller;

import com.unijorge.vendasMysql.dto.EstadoDTO;
import com.unijorge.vendasMysql.model.EstadoModel;
import com.unijorge.vendasMysql.repository.EstadoRepository;
import com.unijorge.vendasMysql.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    private final EstadoService estadoService;

    public EstadoController(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @PostMapping
    public ResponseEntity<String> criarEstado(@RequestBody EstadoDTO estado) {
        EstadoDTO novoEstado = estadoService.criarEstado(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body("Estado criado com sucesso: " + novoEstado.getSigla());
    }

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listarEstados() {
        List<EstadoDTO> estados = estadoService.listarEstados();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        EstadoDTO estado = estadoService.listarEstadoPorID(id);
        if (estado != null) return ResponseEntity.ok(estado);
        return ResponseEntity.notFound().build();
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEstado(@PathVariable Long id, @RequestBody EstadoDTO estadoAtualizado) {
        EstadoDTO estado = estadoService.atualizarEstado(id, estadoAtualizado);

        if (estado != null) return ResponseEntity.ok(estado);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcialEstado(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        EstadoDTO estado = estadoService.atualizarEstadoPatch(id, fields);
        if (estado != null) return ResponseEntity.ok(estado);
        return ResponseEntity.notFound().build();
    }

    // DELETE - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarEstado(@PathVariable Long id) {
        EstadoDTO estado = estadoService.listarEstadoPorID(id);
        if (estado != null) {
            estadoService.deletarEstadoPorID(id);
            return ResponseEntity.ok("Estado com sigla \"" + estado.getSigla() + "\" deletado com sucesso!");
        }
        return ResponseEntity.notFound().build();
    }
}
