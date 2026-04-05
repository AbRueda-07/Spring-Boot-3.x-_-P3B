package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.SaludoResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SaludoService {

    public SaludoResponse crearSaludo(String nombre) {
        String nombreNormalizado = normalizarNombre(nombre);
        String mensaje = "Hola, %s. Bienvenido a Spring Boot 3!".formatted(nombreNormalizado);
        return new SaludoResponse(mensaje, Instant.now());
    }

   
    String normalizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Mundo";
        }

        String limpio = nombre.trim();

        if (limpio.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre no debe contener numeros");
        }

        return limpio.substring(0, 1).toUpperCase() + limpio.substring(1).toLowerCase();
    }
}
