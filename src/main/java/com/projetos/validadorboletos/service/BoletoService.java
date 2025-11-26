package com.projetos.validadorboletos.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.projetos.validadorboletos.exception.BoletoInvalidoException;
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

    boleto.setBanco(extrairBanco(linhaDigitavel));
    boleto.setValor(extrairValor(linhaDigitavel));
    boleto.setVencimento(extrairVencimento(linhaDigitavel));

    return repository.save(boleto);
}

    public List<Boleto> listarTodos() {
        return repository.findAll();
    }

    public Boleto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BoletoInvalidoException("Boleto não encontrado."));
    }

    public Boleto atualizar(Long id, String novaLinhaDigitavel) {
        validator.validar(novaLinhaDigitavel);

        Boleto boleto = buscarPorId(id);
        boleto.setLinhaDigitavel(novaLinhaDigitavel);
        boleto.setValido(true);

        return repository.save(boleto);
    }

    public void deletar(Long id) {
        Boleto boleto = buscarPorId(id);
        repository.delete(boleto);
    }
    
    private String extrairBanco(String linhaDigitavel) {
        return linhaDigitavel.substring(0, 3); //Primeiros 3 digitos do boleto
    }

    private Double extrairValor(String linhaDigitavel) {
        String valorStr = linhaDigitavel.substring(37, 47); //ulimos 10 digitos do boleto
        return Double.parseDouble(valorStr) / 100.0;
    }

    private String extrairVencimento(String linhaDigitavel) {
        String fatorVencimento = linhaDigitavel.substring(33, 37);
        int dias = Integer.parseInt(fatorVencimento);

        if (dias == 0) {
            return null; // Sem data de vencimento
        }
        java.time.LocalDate dataBase = java.time.LocalDate.of(1997, 10, 7);
        java.time.LocalDate dataVencimento = dataBase.plusDays(dias);
        return dataVencimento.toString();
    }


    public static String identificarBanco(String linhaDigitavel) {
    String clean = apenasDigitos(linhaDigitavel);

    if (clean.length() < 3) return "Desconhecido";

    String codigo = clean.substring(0, 3);

    return switch (codigo) {
        case "001" -> "Banco do Brasil";
        case "033" -> "Santander";
        case "104" -> "Caixa Econômica Federal";
        case "212" -> "Banco Original";
        case "237" -> "Bradesco";
        case "341" -> "Itaú";
        default -> "Código " + codigo;
    };
}

    private static String apenasDigitos(String linhaDigitavel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apenasDigitos'");
    }


    public static String linhaDigitavelParaCodigoBarras(String ld) {

    String clean = apenasDigitos(ld);

    if (clean.length() != 47) {
        throw new IllegalArgumentException("A linha digitável deve conter exatamente 47 números.");
    }

    String campo1 = clean.substring(0, 9);
    String campo2 = clean.substring(9, 19);
    String campo3 = clean.substring(19, 29);
    String campo4 = clean.substring(29, 30);
    String campo5 = clean.substring(30);

    return campo1.substring(0, 4) + campo4 + campo5 + campo1.substring(4) + campo2 + campo3;
    }
}