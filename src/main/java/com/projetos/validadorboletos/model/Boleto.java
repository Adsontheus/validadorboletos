package com.projetos.validadorboletos.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String linhaDigitavel;

    private boolean valido;

    private String banco;

    private String vencimento;

    private String valor;

    public Boleto() {}

    public Boleto(String linhaDigitavel, boolean valido, String banco, String vencimento, String valor) {
        this.linhaDigitavel = linhaDigitavel;
        this.valido = valido;
        this.banco = banco;
        this.vencimento = vencimento;
        this.valor = valor;
    }

    // GETTERS E SETTERS
    public Long getId() { return id; }
    public String getLinhaDigitavel() { return linhaDigitavel; }
    public boolean isValido() { return valido; }
    public String getBanco() { return banco; }
    public String getVencimento() { return vencimento; }
    public String getValor() { return valor; }

    public void setId(Long id) { this.id = id; }
    public void setLinhaDigitavel(String linhaDigitavel) { this.linhaDigitavel = linhaDigitavel; }
    public void setValido(boolean valido) { this.valido = valido; }
    public void setBanco(String banco) { this.banco = banco; }
    public void setVencimento(String vencimento) { this.vencimento = vencimento; }
    public void setValor(String valor) { this.valor = valor; }
}