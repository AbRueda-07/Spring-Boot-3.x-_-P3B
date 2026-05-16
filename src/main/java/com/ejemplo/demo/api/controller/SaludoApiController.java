package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.generated.api.WorkshopApi;
import com.ejemplo.demo.generated.model.HealthResponse;
import com.ejemplo.demo.generated.model.SaludoRequest;
import com.ejemplo.demo.generated.model.SaludoResponse;
import com.ejemplo.demo.domain.service.SaludoService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoApiController implements WorkshopApi {

    private final SaludoService saludoService;

    public SaludoApiController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @Override
    public ResponseEntity<HealthResponse> getWorkshopHealth() {
        return ResponseEntity.ok(
            new HealthResponse().estado("ok").mensaje("Workshop Spring Boot activo")
        );
    }

    @Override
    public ResponseEntity<SaludoResponse> saludarPorGet(String nombre) {
        String nombreNormalizado = saludoService.normalizarNombre(nombre);
        String mensaje = saludoService.generarSaludo(nombreNormalizado);
        return ResponseEntity.ok(new SaludoResponse().mensaje(mensaje));
    }

    @Override
    public ResponseEntity<SaludoResponse> saludarPorPost(@Valid SaludoRequest request) {
        String nombreNormalizado = saludoService.normalizarNombre(request.getNombre());
        String mensaje = saludoService.generarSaludo(nombreNormalizado);
        return ResponseEntity.ok(new SaludoResponse().mensaje(mensaje));
    }
}