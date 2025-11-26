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
    private Double valor;
    private String banco;
    private String vencimento;
}