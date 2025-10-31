package com.unijorge.vendasMysql.controller;

import com.unijorge.vendasMysql.model.Estado;
import com.unijorge.vendasMysql.repository.EstadoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final EstadoRepository estadoRepository;

    public HomeController(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    @GetMapping("/")
    public String home(Model model){
        List<Estado> estados = estadoRepository.findAll();
        model.addAttribute("estados", estados);
        return "index";
    }

}
