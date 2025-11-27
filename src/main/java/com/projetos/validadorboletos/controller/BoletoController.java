package com.projetos.validadorboletos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.projetos.validadorboletos.model.Boleto;
import com.projetos.validadorboletos.repository.BoletoUtils;
import com.projetos.validadorboletos.service.BoletoService;
import com.projetos.validadorboletos.repository.BoletoRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {

    private final BoletoService service;
    private final BoletoRepository repo;

    public BoletoController(BoletoService service, BoletoRepository repo) {
        this.service = service;
        this.repo = repo;
    }
    }

    @PostMapping
    public Boleto cadastrar(@RequestBody Map<String, String> body) {
        String linha = body.get("linhaDigitavel");
        return service.cadastrar(linha);
    }

    @GetMapping
    public List<Boleto> listar() {
        return service.listar();
    }

    @GetMapping("/validar")
    public Map<String, Object> validar(@RequestParam String linhaDigitavel) {
        boolean valido = service.validar(linhaDigitavel);

        return Map.of(
                "linhaDigitavel", linhaDigitavel,
                "valido", valido,
                "banco", BoletoUtils.identificarBanco(linhaDigitavel)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok("Boleto deletado com sucesso.");
        }
        return ResponseEntity.status(404).body("Boleto não encontrado.");
    }
}