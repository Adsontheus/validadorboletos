package com.projetos.validadorboletos.controller;

import com.projetos.validadorboletos.model.Boleto;
import com.projetos.validadorboletos.service.BoletoService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {

    private final BoletoService service;

    public BoletoController(BoletoService service) {
        this.service = service;
    }

    @PostMapping("/validar")
    public Boleto validar(@RequestParam @NotBlank String linhaDigitavel) {
        return service.validarBoleto(linhaDigitavel);
    }
}