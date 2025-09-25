package com.emmanuel.miniBancoDigital.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@ControllerAdvice // Marca a classe para tratar exceções globalmente em todos os controllers
public class GlobalExceptionHandler {

    // Captura exceções lançadas pelo banco quando há violação de restrições(ex: CPF/email duplicado)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicateKey(DataIntegrityViolationException ex){
        // Retorna HTTP 400 (Bad Request) com mensagem amigável
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("CPF ou email já cadastrado");// Mensagem personalizada pro cliente da API
    }

    // Captura exceções do tipo ResponseStatusException lançadas no service
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleNotFound(ResponseStatusException ex){
        // Usa o status que foi definido na exceção(ex: 404) e devolve a mensagem
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }

    // Captura erros de validação do @Valid nos controllers
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex){
        // Junta todas as mensagens de erro dos campos em uma única string
        String msg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())// Ex: "Nome não pode estar vazio
                .collect(Collectors.joining(", "));

        // Retorna HTTP 400 (Bad Request) com todas as mensagens de validação
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(msg);
    }
}
