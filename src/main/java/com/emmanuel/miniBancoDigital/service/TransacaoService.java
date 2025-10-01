package com.emmanuel.miniBancoDigital.service;

import com.emmanuel.miniBancoDigital.enums.TipoTransacao;
import com.emmanuel.miniBancoDigital.model.Conta;
import com.emmanuel.miniBancoDigital.model.Transacao;
import com.emmanuel.miniBancoDigital.repository.ContaRepository;
import com.emmanuel.miniBancoDigital.repository.TransacaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public Transacao depositar(Long idContaDestino, BigDecimal valor){

        // Buscar a conta de destino no banco usando ContaRepository
        // findById retorna um Optional<Conta>, e orElseThrow lança exceção se não encontrar
        Conta contaDestino = contaRepository.findById(idContaDestino)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "conta não encontrada"));

        // Validar que o valor do depósito é positivo
        // compareTo retorna -1 se menor, 0 se igual, 1 se maior
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(BAD_REQUEST, "Valor inválido para depósito");
        }

        // Atualizar o saldo da conta destino
        // getSaldo() pega o saldo atual e add(valor) soma o depósito.
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        // Salva a conta atualizada no banco
        contaRepository.save(contaDestino);

        // Cria um registro de transação para histórico
        Transacao transacao = new Transacao();
        transacao.setContaDestino(contaDestino); // Conta que recebeu o depósito
        transacao.setValor(valor); // valor da operação
        transacao.setTipoTransacao(TipoTransacao.DEPOSITO); // Tipo de transação
        transacao.setDataHora(LocalDateTime.now()); // Registra o horário da transação

        // Salvar a transação no banco e retornar
        return transacaoRepository.save(transacao);

    }

    public Transacao sacar(Long idContaOrigem, BigDecimal valor){

        // Buscar conta de origem no banco
        // orElseThrow garante que se a conta não existir, retorna 404 automaticamente
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Conta não encontrada"));

        // Validar valor positivo
        if (valor.compareTo(BigDecimal.ZERO) < 0){
            throw new ResponseStatusException(BAD_REQUEST, "Valor inválido para saque");

        }

        // Verificar se a conta tem saldo suficiente
        if (contaOrigem.getSaldo().compareTo(valor) < 0){
            throw new ResponseStatusException(BAD_REQUEST, "Saldo insuficiente");
        }

        // Subtrai o valor do saldo da conta
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));

        // Salvar a conta atualizada no banco
        contaRepository.save(contaOrigem);

        // Criar registro de transação
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(contaOrigem); // Conta que realizou saque
        transacao.setValor(valor); // Valor do saque
        transacao.setTipoTransacao(TipoTransacao.SAQUE); // Tipo de transação
        transacao.setDataHora(LocalDateTime.now()); // Data/hora do saque

        // Salvar a transação no banco e retornar
        return transacaoRepository.save(transacao);

    }

    public Transacao transferir(Long idContaOrigem, Long idContaDestino, BigDecimal valor){

        // Buscar conta de origem
        // Se não existir, lança 404
        Conta contaOrigem = contaRepository.findById(idContaOrigem)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Conta não encontrada"));

        // Buscar conta de destino
        Conta contaDestino = contaRepository.findById(idContaDestino)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Conta não encontrada"));

        // Validar que as contas são diferentes
        if (idContaOrigem.equals(idContaDestino)){
            throw new ResponseStatusException(BAD_REQUEST, "Conta de origem e destino devem ser diferentes");
        }

        // Validar valor positivo
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(BAD_REQUEST, "Valor da transferência inválido");
        }

        // Validar saldo suficiente na conta origem
        if (contaOrigem.getSaldo().compareTo(valor) < 0){
            throw new ResponseStatusException(BAD_REQUEST, "Saldo insuficiente para transferência");
        }

        // Atualizar saldos das contas
        contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(valor));
        contaDestino.setSaldo(contaDestino.getSaldo().add(valor));

        // salvar contas atualizadas no banco
        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);

        // Criar registro da transação
        Transacao transacao = new Transacao();
        transacao.setContaOrigem(contaOrigem); // Conta que enviou o dinheiro
        transacao.setContaDestino(contaDestino); // Conta que recebeu o dinheiro
        transacao.setValor(valor); // Valor transferido
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA); // Tipo da transação
        transacao.setDataHora(LocalDateTime.now()); // Data/hora da transferência

        // Salvar a transação no banco e retornar
        return transacaoRepository.save(transacao);
    }
}
