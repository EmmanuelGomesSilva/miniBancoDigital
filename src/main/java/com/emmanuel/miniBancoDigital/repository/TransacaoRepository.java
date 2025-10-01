package com.emmanuel.miniBancoDigital.repository;

import com.emmanuel.miniBancoDigital.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Repository para Transacao
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
