package com.emmanuel.miniBancoDigital.service;


import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.repository.ClienteRepository;
import com.emmanuel.miniBancoDigital.repository.ContaRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service // Marca a classe como service no Spring
@AllArgsConstructor //Injeta o repository via construtor
public class ClienteService {
    private final ClienteRepository repository;
    private final ContaRepository contaRepository;

    // Cria um cliente e retorna o mesmo
    public Cliente create(Cliente cliente) {
        try {
            return repository.save(cliente); // Tenta salvar no banco
        } catch (DataIntegrityViolationException e) {
            // se CPF ou email duplicado, lança exceção que será tratada globalmente
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF ou email já cadastrado");
        }

    }

    // Lista todos os clientes ordenados pelo nome e email
    public List<Cliente> list() {
        Sort sort = Sort.by("nome").ascending();
        return repository.findAll(sort);

    }

    public Cliente findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
    }

    // Atualiza um cliente existente
    public Cliente update(Long id, Cliente clienteAtualizado) {

        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setEmail(clienteAtualizado.getEmail());

        try {
            return repository.save(clienteExistente); // Salva as alterações
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF ou email já cadastrado");
        }

    }

    public void delete(Long id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));

        // Deleta todas as contas associadas
        contaRepository.deleteAllByCliente(cliente);

        // Deleta o cliente
        repository.delete(cliente);



    }
}
