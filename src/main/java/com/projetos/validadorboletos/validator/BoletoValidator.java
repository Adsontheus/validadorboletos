package com.projetos.validadorboletos.validator;

import org.springframework.stereotype.Component;

import com.projetos.validadorboletos.exception.BoletoInvalidoException;

@Component
public class BoletoValidator {

    public void validar(String linhaDigitavel) {

        if (linhaDigitavel == null || !linhaDigitavel.matches("\\d{47}")) {
            throw new BoletoInvalidoException("A linha digitável deve conter exatamente 47 números.");
        }

        // Validação do dígito verificador geral (módulo 11)
        if (!validarDV(linhaDigitavel)) {
            throw new BoletoInvalidoException("Dígito verificador inválido.");
        }

    }

    private boolean validarDV(String linha) {
        int dv = Character.getNumericValue(linha.charAt(32));
        String bloco = linha.substring(0, 32);

        int multiplicador = 2;
        int soma = 0;

        for (int i = bloco.length() - 1; i >= 0; i--) {
            int numero = Character.getNumericValue(bloco.charAt(i));
            soma += numero * multiplicador;
            multiplicador = multiplicador == 9 ? 2 : multiplicador + 1;
        }

        int resto = soma % 11;
        int dvCalculado = (resto == 0 || resto == 1) ? 0 : 11 - resto;

        return dv == dvCalculado;
    }
}