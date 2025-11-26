package com.projetos.validadorboletos.service;


import org.springframework.stereotype.Service;

import com.projetos.validadorboletos.model.Boleto;
import com.projetos.validadorboletos.repository.BoletoRepository;
import com.projetos.validadorboletos.validator.BoletoValidator;

@Service
public class BoletoService {

    private final BoletoValidator validator;
    private final BoletoRepository repository;

    public BoletoService(BoletoValidator validator, BoletoRepository repository) {
        this.validator = validator;
        this.repository = repository;
    }

    public Boleto validarBoleto(String linhaDigitavel) {
        validator.validar(linhaDigitavel);

        Boleto boleto = new Boleto();
        boleto.setLinhaDigitavel(linhaDigitavel);
        boleto.setValido(true);

        return repository.save(boleto);
    }
}