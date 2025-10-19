package com.emmanuel.miniBancoDigital.service;


import com.emmanuel.miniBancoDigital.model.Cliente;
import com.emmanuel.miniBancoDigital.model.Conta;
import com.emmanuel.miniBancoDigital.repository.ClienteRepository;
import com.emmanuel.miniBancoDigital.repository.ContaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@Service
@AllArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;
    private final ClienteRepository clienteRepository;


    public Conta create(Conta conta ) {
        Cliente cliente = clienteRepository.findById(conta.getCliente().getId())
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));
        conta.setCliente(cliente);

        try {
            Conta salva = contaRepository.save(conta);
            log.info("Conta ID {} criada no banco");
            return salva;
        } catch (DataIntegrityViolationException e) {
            log.info("Erro ao salvar conta: Número dupicado", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número da conta já cadastrado");
        }

    }

    // Lista todas as contas
    public List<Conta> list() {
        log.info("Buscando todas as contas no banco");
        return contaRepository.findAll();
    }

    public Conta findById(Long id){
        log.info("Buscando conta ID {}", id);
        return contaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Conta não encontrada"));
    }

    // Atualiza os dados de uma conta existente
    public Conta update(Long id,  Conta contaNova)  {
        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não cadastrada"));

        if (contaNova.getCliente() != null){
            Cliente clienteexistente = clienteRepository.findById(contaNova.getCliente().getId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado"));

            contaExistente.setCliente(clienteexistente);
        }

        // Atualiza demais campos
        contaExistente.setNumero(contaNova.getNumero());
        contaExistente.setTipoConta(contaNova.getTipoConta());
        contaExistente.setSaldo(contaNova.getSaldo());

        // Tenta salvar, tratando conflito de número
        try {
            Conta salva = contaRepository.save(contaExistente);
            log.info("Conta ID {} atualizada com sucesso", salva.getId());
            return salva;
        } catch (DataIntegrityViolationException e) {
            log.info("Erro ao atualizar conta: número duplicado", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Número da conta já cadastrado");
        }

    }

    // Deleta uma conta pelo ID
    public void delete(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Conta não encontrada");
        }
        log.info("Deletando conta ID {}", id);
        contaRepository.deleteById(id);
    }


}
