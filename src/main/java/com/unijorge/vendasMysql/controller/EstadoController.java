package com.unijorge.vendasMysql.controller;

import com.unijorge.vendasMysql.model.Estado;
import com.unijorge.vendasMysql.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoRepository estadoRepository;

    @PostMapping
    public ResponseEntity<Estado> criarEstado(@RequestBody Estado estado) {
        Estado novoEstado = estadoRepository.save(estado);
        return ResponseEntity.ok(novoEstado);
    }

    @GetMapping
    public ResponseEntity<List<Estado>> listarEstados() {
        List<Estado> estados = estadoRepository.findAll();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscarPorId(@PathVariable Long id) {
        Optional<Estado> estado = estadoRepository.findById(id);
        if (estado.isPresent()) {
            return ResponseEntity.ok(estado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Estado> atualizarEstado(@PathVariable Long id, @RequestBody Estado estadoAtualizado) {
        Optional<Estado> estadoExistente = estadoRepository.findById(id);

        if (estadoExistente.isPresent()) {
            Estado estado = estadoExistente.get();
            estado.setSigla(estadoAtualizado.getSigla());
            estado.setDescricao(estadoAtualizado.getDescricao());
            estadoRepository.save(estado);
            return ResponseEntity.ok(estado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstado(@PathVariable Long id) {
        if (estadoRepository.existsById(id)) {
            estadoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
