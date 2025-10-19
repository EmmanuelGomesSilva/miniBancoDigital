package com.emmanuel.miniBancoDigital.controller;


import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    @Operation(summary = "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody @Valid Cliente cliente){
        log.info("Criando cliente com CPF {}", cliente.getCpf());
        Cliente criado = service.create(cliente);
        log.info("Criando cliente com ID {}", cliente.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> list(){
        log.info("Recebendo requisição para listar todas os clientes");
        List<Cliente> clientes = service.list();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        log.info("Recebendo requisição para buscar cliente ID {}", id);
        Cliente cliente = service.findById(id);
        log.info("Cliente ID {} retornado com sucesso", id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody @Valid Cliente clienteNovo){
        log.info("Recebendo requisição para atualizar cliente ID {}",id);
        Cliente atualizado = service.update(id, clienteNovo);
        log.info("Cliente ID {} atualizado com sucesso", atualizado.getId());
        return ResponseEntity.ok(atualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Recebendo requisição para deletar cliente pelo ID {}", id);
        service.delete(id);
        log.info("Cliente ID {} deletado com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}
