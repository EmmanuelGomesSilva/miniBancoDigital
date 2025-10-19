package com.emmanuel.miniBancoDigital.controller;


import com.emmanuel.miniBancoDigital.model.Conta;
import com.emmanuel.miniBancoDigital.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contas", description = "Endpoints para gerenciar contas bancárias")
@Slf4j
@RestController
@RequestMapping("/contas")
@AllArgsConstructor
public class ContaController {
    private final ContaService service;

    @Operation(summary = "Cria uma nova conta")
    @PostMapping
    public ResponseEntity<Conta> create(@RequestBody @Valid Conta conta){

        log.info("Criando conta para cliente ID {}", conta.getCliente().getId());
        Conta criada = service.create(conta);
        log.info("Conta criada com ID {}", criada.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @Operation(summary = "Lista todas as contas no banco")
    @GetMapping
    public ResponseEntity<List<Conta>> list(){
        log.info("Recebendo requisição para listar todas as contas");
        List<Conta> contas = service.list();
        return ResponseEntity.ok(contas);
    }

    @Operation(summary = "Busca conta pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Conta> findById(@PathVariable Long id){
        log.info("Recebendo requisição para buscar conta ID {}", id);
        Conta conta = service.findById(id);
        return  ResponseEntity.ok(conta);

    }

    @Operation(summary = "Atualiza conta pelo ID")
    @PutMapping ("/{id}")
    public ResponseEntity<Conta> update(@PathVariable Long id, @RequestBody @Valid Conta contaNova){
        log.info("Recebendo requisição para atualizar conta ID {}", id);
        Conta atualizada =  service.update(id, contaNova);
        log.info("conta ID {} atualziada com sucesso", atualizada.getId());
        return ResponseEntity.ok(atualizada);
    }

    @Operation(summary = "Deleta conta pelo ID")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Recebendo requisição para deletar conta ID {}", id);
        service.delete(id);
        log. info("Conta ID {} deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }

}
