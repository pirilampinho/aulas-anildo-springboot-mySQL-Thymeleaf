package com.unijorge.vendasMysql.controller;

import com.unijorge.vendasMysql.dto.EstadoDTO;
import com.unijorge.vendasMysql.model.EstadoModel;
import com.unijorge.vendasMysql.repository.EstadoRepository;
import com.unijorge.vendasMysql.service.EstadoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estados/ui")
public class EstadoControllerUI {
    private final EstadoRepository estadoRepository;
    private final EstadoService estadoService;

    public EstadoControllerUI(EstadoRepository estadoRepository, EstadoService estadoService) {
        this.estadoRepository = estadoRepository;
        this.estadoService = estadoService;
    }

    @GetMapping("/listar")
    public String home(Model model){
        List<EstadoModel> estados = estadoRepository.findAll();
        model.addAttribute("estados", estados);
        return "index";
    }

    @GetMapping("/criar")
    public String mostrarFormularioAdicionar(Model model) {
        model.addAttribute("estados", new EstadoModel());
        return "cadastrar";
    }

    @PostMapping("/salvar")
    public String salvarEstado(@ModelAttribute("estados") EstadoModel estado, Model model) {
        EstadoModel estadoSalvo = estadoRepository.save(estado);

        if (estadoSalvo == null) {
            model.addAttribute("mensagem", "Ocorreu um erro!");
            return "cadastrar";
        }

        model.addAttribute("mensagem", "Estado cadastrado com sucesso!");
        return "redirect:/estados/ui/listar";
    }

    @GetMapping("/alterar/{id}")
    public String exibirFormAlterarEstado(@PathVariable Long id, Model model) {
        Optional<EstadoModel> estado = estadoRepository.findById(id);
        if (estado.isPresent()) {
            model.addAttribute("estados", estado);
            return "editar";
        }
        return "redirect:/estados/ui/listar";
    }

    @PostMapping("/atualizar")
    public String atualizarEstado(@ModelAttribute EstadoDTO estadoDTO, Model model) {
        EstadoDTO estadoAtualizado = estadoService.atualizarEstado(estadoDTO.getId(), estadoDTO);
        if (estadoAtualizado != null) return "redirect:/estados/ui/listar";
        return "editar";
    }

    @GetMapping("/deletar/{id}")
    public String paagar(@PathVariable Long id) {
        estadoRepository.deleteById(id);
        return "redirect:/estados/ui/listar";
    }
}
