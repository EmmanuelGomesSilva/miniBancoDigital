package com.emmanuel.miniBancoDigital.controller;


import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.model.Conta;
import com.emmanuel.miniBancoDigital.service.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody @Valid Cliente cliente){
        Cliente criado = service.create(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }


    @GetMapping
    public ResponseEntity<List<Cliente>> list(){
        List<Cliente> clientes = service.list();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id){
        Cliente cliente = service.findById(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody @Valid Cliente clienteNovo){
        Cliente atualizado = service.update(id, clienteNovo);
        return ResponseEntity.ok(atualizado);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
