package com.unijorge.vendasMysql.controller;

import com.unijorge.vendasMysql.model.Cliente;
import com.unijorge.vendasMysql.model.Estado;
import com.unijorge.vendasMysql.repository.ClienteRepository;
import com.unijorge.vendasMysql.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        if (cliente.getEstado() != null && cliente.getEstado().getId() != null) {
            Estado estado = estadoRepository.findById(cliente.getEstado().getId())
                    .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
            cliente.setEstado(estado); // garante que está anexado ao contexto
        }
        Cliente salvo = clienteRepository.save(cliente);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        Optional<Cliente> clienteExistenteOpt = clienteRepository.findById(id);

        if (clienteExistenteOpt.isPresent()) {
            Cliente clienteExistente = clienteExistenteOpt.get();
            // Atualiza o nome
            clienteExistente.setNome(clienteAtualizado.getNome());
            // Atualiza o estado (se informado)
            if (clienteAtualizado.getEstado() != null && clienteAtualizado.getEstado().getId() != null) {
                Estado estado = estadoRepository.findById(clienteAtualizado.getEstado().getId())
                        .orElseThrow(() -> new RuntimeException("Estado não encontrado"));
                clienteExistente.setEstado(estado);
            }

            Cliente atualizado = clienteRepository.save(clienteExistente);
            return ResponseEntity.ok(atualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public HttpEntity<String> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        //return ResponseEntity.ok(clientes);
        if(clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Lista de clientes vazia!");
        }else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clientes.toString());
        }
    }

    @DeleteMapping("/{id}")
    public HttpEntity<String> deletarCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            //return ResponseEntity.noContent().build();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Cliente deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Estado não encontrado!");
        }
    }
}
