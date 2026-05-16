package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.generated.api.SimulacionesApi;
import com.ejemplo.demo.generated.model.PrestamoRequest;
import com.ejemplo.demo.generated.model.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrestamoApiController implements SimulacionesApi {


	 private final PrestamoService prestamoService;

	    public PrestamoApiController(PrestamoService prestamoService) {
	        this.prestamoService = prestamoService;
	    }
	    
    @Override
    public ResponseEntity<PrestamoResponse> simularPrestamo(PrestamoRequest request) {
        PrestamoResponse response = prestamoService.simular(
            request.getMonto(),
            request.getTasaAnual(),
            request.getMeses()
        );
        return ResponseEntity.ok(response);
    }
}
