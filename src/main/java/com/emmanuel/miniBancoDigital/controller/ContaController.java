package com.emmanuel.miniBancoDigital.controller;


import com.emmanuel.miniBancoDigital.model.Conta;
import com.emmanuel.miniBancoDigital.service.ContaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Informa ao Spring que esta classe é um controller REST
@RequestMapping("/contas") // Todas as rotas aqui começam com /contas
@AllArgsConstructor // Gera o construtor automaticamente para injetar o service
public class ContaController {
    private final ContaService service;

    // Cria nova conta vinculada a um cliente
    @PostMapping
    public Conta create(@RequestBody @Valid Conta conta){
        // Recebe os dados da conta no corpo da requisição e o ID do cliente associado
        return service.create(conta, conta.getCliente().getId());
    }

    // Lista todas as contas cadastradas
    @GetMapping
    public List<Conta> list(){
        // Retorna todas as contas do banco
        return service.list();
    }

    // Atualiza conta existente
    @PutMapping
    public Conta update(@RequestBody @Valid Conta conta){
        // Receb os dados novo da conta e o cliente atualizado
        return service.update(conta, conta.getCliente());
    }


    // Deleta uma conta pelo ID
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        // Deleta a conta correspondente ao ID informado na URL
        service.delete(id);
    }

}
