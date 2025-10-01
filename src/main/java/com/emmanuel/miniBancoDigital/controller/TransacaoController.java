package com.emmanuel.miniBancoDigital.controller;

import com.emmanuel.miniBancoDigital.model.Conta;
import com.emmanuel.miniBancoDigital.model.Transacao;
import com.emmanuel.miniBancoDigital.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transacoes")
@AllArgsConstructor
public class TransacaoController {
    private final TransacaoService service;

    // Depósito em uma conta
    @PostMapping("/depositar")
    public Transacao depositar(@RequestParam Long idConta, @RequestParam BigDecimal valor){
        return service.depositar(idConta, valor);
    }

    // Saque em uma conta
    @PostMapping("/sacar")
    public Transacao sacar(@RequestParam Long idConta, @RequestParam BigDecimal valor){
        return service.sacar(idConta, valor);
    }

    // Transferência entre contas
    @PostMapping("/transferir")
    public Transacao transferir(@RequestParam Long idOrigem, @RequestParam Long idDestino, @RequestParam BigDecimal valor){
       return service.transferir(idOrigem, idDestino, valor);
    }




}
