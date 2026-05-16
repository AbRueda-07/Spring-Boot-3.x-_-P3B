package com.ejemplo.demo.api.exception;

import com.ejemplo.demo.generated.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<String, String> MENSAJES_CAMPO = Map.ofEntries(
        Map.entry("nombre", "El nombre es obligatorio"),
        Map.entry("monto", "El monto debe ser mayor que 0"),
        Map.entry("tasaAnual", "La tasa anual debe ser mayor que 0"),
        Map.entry("meses", "Los meses deben estar entre 1 y 360")
    );

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
        
        Map<String, String> detalles = new HashMap<>();
        
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String mensaje = MENSAJES_CAMPO.getOrDefault(error.getField(), error.getDefaultMessage());
            detalles.put(error.getField(), mensaje);
        }

        ErrorResponse res = new ErrorResponse();
        res.setCodigo("VALIDATION_ERROR");
        res.setMensaje("Uno o mas campos son invalidos");
        res.setTimestamp(OffsetDateTime.now());
        res.setDetalles(detalles);  
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarReglaDeNegocio(IllegalArgumentException ex) {
        ErrorResponse res = new ErrorResponse();
        res.setCodigo("BUSINESS_RULE_ERROR");
        res.setMensaje(ex.getMessage());
        res.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGenerica(Exception ex) {
        ErrorResponse res = new ErrorResponse();
        res.setCodigo("INTERNAL_ERROR");
        res.setMensaje("Ocurrio un error interno");
        res.setTimestamp(OffsetDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
}
