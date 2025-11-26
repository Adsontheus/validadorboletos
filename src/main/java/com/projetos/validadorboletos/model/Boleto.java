package com.projetos.validadorboletos.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String linhaDigitavel;
    private boolean valido;

    private String banco;
    private Double valor;
    private String vencimento;
}