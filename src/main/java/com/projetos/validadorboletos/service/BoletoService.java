package com.projetos.validadorboletos.service;

import org.springframework.stereotype.Service;

import com.projetos.validadorboletos.model.Boleto;
import com.projetos.validadorboletos.repository.BoletoRepository;
import com.projetos.validadorboletos.repository.BoletoUtils;

@Service
public class BoletoService {

    private final BoletoRepository repo;

    public BoletoService(BoletoRepository repo) {
        this.repo = repo;
    }

    // 🔹 Cadastra boleto extraindo tudo automaticamente
    public Boleto cadastrar(String linhaDigitavel) {

        boolean valido = BoletoUtils.validarLinha(linhaDigitavel);

        String banco = BoletoUtils.identificarBanco(linhaDigitavel);

        String codigoBarras = BoletoUtils.linhaDigitavelParaCodigoBarras(linhaDigitavel);

        String vencimento = BoletoUtils.extrairVencimento(codigoBarras);

        String valor = BoletoUtils.extrairValor(codigoBarras);

        Boleto boleto = new Boleto(linhaDigitavel, valido, banco, vencimento, valor);

        return repo.save(boleto);
    }

    public java.util.List<Boleto> listar() {
        return repo.findAll();
    }

    public boolean validar(String linhaDigitavel) {
        return BoletoUtils.validarLinha(linhaDigitavel);
    }
}
