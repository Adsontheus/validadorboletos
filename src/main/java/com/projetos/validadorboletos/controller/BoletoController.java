package com.projetos.validadorboletos.controller;

import com.projetos.validadorboletos.model.Boleto;
import com.projetos.validadorboletos.service.BoletoService;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {

    private final BoletoService service;

    public BoletoController(BoletoService service) {
        this.service = service;
    }

    // Criar e validar
    @PostMapping("/validar")
    public Boleto validar(@RequestParam @NotBlank String linhaDigitavel) {
        return service.validarBoleto(linhaDigitavel);
    }

    // Listar todos
    @GetMapping
    public List<Boleto> listarTodos() {
        return service.listarTodos();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Boleto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Atualizar boleto
    @PutMapping("/{id}")
    public Boleto atualizar(
            @PathVariable Long id,
            @RequestParam @NotBlank String novaLinhaDigitavel
    ) {
        return service.atualizar(id, novaLinhaDigitavel);
    }

    // Deletar boleto
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}