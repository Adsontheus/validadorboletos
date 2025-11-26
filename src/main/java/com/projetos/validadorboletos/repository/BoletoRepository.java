package com.projetos.validadorboletos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projetos.validadorboletos.model.Boleto;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {
}
