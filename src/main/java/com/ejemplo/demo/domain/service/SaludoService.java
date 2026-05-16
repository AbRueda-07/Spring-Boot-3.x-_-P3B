package com.ejemplo.demo.domain.service;

import org.springframework.stereotype.Service;

@Service
public class SaludoService {  

    public String normalizarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "Mundo";
        }
        return nombre.strip().substring(0,1).toUpperCase() + 
               nombre.strip().substring(1).toLowerCase();
    }

    public String generarSaludo(String nombre) {
        return "Hola, " + nombre + ". Bienvenido a Spring Boot 3!";  
    }
}