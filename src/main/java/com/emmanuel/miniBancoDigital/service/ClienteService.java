package com.emmanuel.miniBancoDigital.service;


import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.repository.ClienteRepository;
import com.emmanuel.miniBancoDigital.repository.ContaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final ContaRepository contaRepository;


    public Cliente create(Cliente cliente) {
        log.info("Cliente criado com CPF {}", cliente.getCpf());
        try {
            Cliente salvo = repository.save(cliente);
            log.info("Cliente criado com ID {}", cliente.getId());
            return salvo;
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao criar cliente: CPF ou email duplicado {}", cliente.getCpf());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF ou email já cadastrado");
        }

    }

    public List<Cliente> list() {
        log.info("Recebendo requisição para listar todos os clientes");
        Sort sort = Sort.by("nome").ascending();
        List<Cliente> clientes = repository.findAll(sort);
        log.info("total de {} clientes retornados", clientes.size());
        return clientes;

    }

    public Cliente findById(Long id) {
        log.info("Buscando cliente ID {}", id);
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente ID {} não encontrado", id);
                    return new ResponseStatusException(NOT_FOUND, "Cliente não encontrado");
                });
        log.info("Cliente ID {} retornado com sucesso", id);
        return cliente;

    }


    public Cliente update(Long id, Cliente clienteAtualizado) {
        log.info("Atualizando cliente ID {}", id);
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente ID {} não encontrado para update", id);
                    return new ResponseStatusException(NOT_FOUND, "Cliente não encontrado");
                });

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setEmail(clienteAtualizado.getEmail());

        try {
            Cliente atualizado = repository.save(clienteExistente);
            log.info("Cliente ID {} atualizado com sucesso", atualizado.getId());
            return atualizado;
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao atualizar cliente ID {}: CPF ou email duplicado", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF ou email já cadastrado");
        }

    }

    public void delete(Long id) {
        log.info("Deletando cliente ID {}", id);
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cliente ID {} não encontrado para delete", id);
                    return new ResponseStatusException(NOT_FOUND, "Cliente não encontrado");
                });

        contaRepository.deleteAllByCliente(cliente);
        repository.delete(cliente);
        log.info("Cliente ID {} deletado com sucesso", id);


    }
}
