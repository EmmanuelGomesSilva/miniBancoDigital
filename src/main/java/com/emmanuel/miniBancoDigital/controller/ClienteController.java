package com.emmanuel.miniBancoDigital.controller;


import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Define endpoints REST
@AllArgsConstructor // Injeta o service via construtor
@RequestMapping("/clientes") // Rota base para todos os endpoints
public class ClienteController {
    private final ClienteService service;

    // POST /clientes - cria um novo cliente
    @PostMapping
    public Cliente create(@RequestBody @Valid Cliente cliente){
        // @valid valida os campos obrigatórios(nome, cpf, email)
        // O service cuida de salvar e tratar duplicidade
        return service.create(cliente); // Chama o service
    }

    //GET /clientes - lista todos os clientes
    @GetMapping
    public List<Cliente> list(){
        // Retorna todos os clientes ordenados pelo nome
        return service.list();
    }


    //PUT /clientes - atualiza um cliente existente
    @PutMapping
    public Cliente update(@RequestBody @Valid Cliente clienteAtualizado){
        // @Valid garante que os campos obrigatórios sejam preenchidos
        // O service cuida de verificar existência e duplicidade
        return service.update(clienteAtualizado);
    }

    // DELETE /clientes/{id} - deleta um cliente pelo ID
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        // O service cuida de verificar se o cliente existe antes de deletar
        service.delete(id);
    }
}
