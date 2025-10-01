package com.emmanuel.miniBancoDigital.repository;

import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Repository para conta (permite acessar os dados do banco)
public interface ContaRepository extends JpaRepository<Conta, Long> {
    void deleteAllByCliente(Cliente cliente);
}
