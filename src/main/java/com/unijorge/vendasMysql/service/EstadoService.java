package com.unijorge.vendasMysql.service;

import com.unijorge.vendasMysql.dto.EstadoDTO;
import com.unijorge.vendasMysql.mapper.EstadoMapper;
import com.unijorge.vendasMysql.model.EstadoModel;
import com.unijorge.vendasMysql.repository.EstadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadoService {
    private final EstadoRepository estadoRepository;
    private final EstadoMapper estadoMapper;

    public EstadoService(EstadoRepository estadoRepository, EstadoMapper estadoMapper) {
        this.estadoRepository = estadoRepository;
        this.estadoMapper = estadoMapper;
    }

    public List<EstadoDTO> listarEstados() {
        List<EstadoModel> estados = estadoRepository.findAll();
        return estados.stream().map(estadoMapper::map).collect(Collectors.toList());
    }

    public EstadoDTO listarEstadoPorID(Long id) {
        Optional<EstadoModel> estado = estadoRepository.findById(id);
        return estado.map(estadoMapper::map).orElse(null);
    }

    public EstadoDTO criarEstado(EstadoDTO estadoDTO) {
        EstadoModel estado = estadoMapper.map(estadoDTO);
        estado = estadoRepository.save(estado);
        return estadoMapper.map(estado);
    }

    public void deletarEstadoPorID(Long id) {
        estadoRepository.deleteById(id);
    }

    public EstadoDTO atualizarEstado(Long id, EstadoDTO estadoDTO) {
        Optional<EstadoModel> estadoExistente = estadoRepository.findById(id);
        if (estadoExistente.isPresent()) {
            EstadoModel estadoAtualizado = estadoMapper.map(estadoDTO);
            estadoAtualizado.setId(id);

            EstadoModel estadoSalvo = estadoRepository.save(estadoAtualizado);
            return estadoMapper.map(estadoSalvo);
        }
        return null;
    }

    public EstadoDTO atualizarEstadoPatch(Long id, Map<String, Object> fields) {
        EstadoModel estadoAtualizar = estadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Estado não encontrado!"));
        fields.forEach((nome, valor) -> {
            switch (nome) {
                case "sigla":
                    estadoAtualizar.setSigla((String) valor);
                    break;
                case "descricao":
                    estadoAtualizar.setDescricao((String) valor);
                    break;
            }
        });
        return estadoMapper.map(estadoRepository.save(estadoAtualizar));
    }
}