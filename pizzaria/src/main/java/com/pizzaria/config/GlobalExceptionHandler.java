package com.pizzaria.config;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("mensagem", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarIntegridade(DataIntegrityViolationException ex) {
        String detalhe = ex.getMostSpecificCause().getMessage();
        if (detalhe != null && detalhe.toLowerCase().contains("cpf")) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "CPF já cadastrado."));
        }
        if (detalhe != null && detalhe.toLowerCase().contains("usuario")) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Usuário já cadastrado."));
        }
        if (detalhe != null && detalhe.toLowerCase().contains("nome")) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Nome já cadastrado."));
        }
        if (detalhe != null && detalhe.toLowerCase().contains("duplicate")) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Nome já cadastrado."));
        }
        if (detalhe != null && (detalhe.toLowerCase().contains("too long")
                || detalhe.toLowerCase().contains("data truncation")
                || detalhe.toLowerCase().contains("packet"))) {
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Imagem muito grande. Use um arquivo menor."));
        }
        return ResponseEntity.badRequest().body(Map.of("mensagem", "Dados já cadastrados no sistema."));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> tratarNaoEncontrado(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", ex.getMessage()));
    }
}
