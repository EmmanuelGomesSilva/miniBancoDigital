package com.emmanuel.miniBancoDigital.service;


import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.model.Conta;
import com.emmanuel.miniBancoDigital.repository.ClienteRepository;
import com.emmanuel.miniBancoDigital.repository.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

// Classe de serviço que contém as regras de negócio relacionadas à Conta
@Service
@AllArgsConstructor
public class ContaService {
    private final ContaRepository repository;
    private final ClienteRepository clienteRepository;

    // Cria uma conta vinculada a um cliente
    public Conta create(Conta conta, Long idCliente ) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
        conta.setCliente(cliente);
        try {
            return repository.save(conta);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número da conta já cadastrado");
        }

    }

    // Lista todas as contas
    public List<Conta> list() {
        return repository.findAll();
    }

    // Atualiza os dados de uma conta existente
    public Conta update(Conta contaNova, Cliente clienteNovo) {
        Conta contaExistente = repository.findById(contaNova.getId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Conta não cadastrada"));

        Cliente clienteExistente = clienteRepository.findById(clienteNovo.getId())
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));


        contaExistente.setCliente(clienteExistente);
        contaExistente.setNumero(contaNova.getNumero());
        contaExistente.setTipoConta(contaNova.getTipoConta());
        contaExistente.setSaldo(contaNova.getSaldo());

        try {
            return repository.save(contaExistente);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número da conta já cadastrado");
        }

    }

    // Deleta uma conta pelo ID
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Conta não encontrada");
        }
        repository.deleteById(id);
    }


}
