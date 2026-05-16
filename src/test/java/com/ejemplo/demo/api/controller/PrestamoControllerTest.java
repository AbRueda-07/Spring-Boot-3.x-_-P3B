package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.exception.GlobalExceptionHandler;
import com.ejemplo.demo.domain.service.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoApiController.class)
@Import({PrestamoService.class, GlobalExceptionHandler.class})
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/v1/simulaciones/prestamo debe responder simulacion correcta")
    void simularPrestamo_ok() throws Exception {
        Map<String, Object> request = Map.of(
                "monto", 10000,
                "tasaAnual", 12,
                "meses", 12
        );

        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuotaMensual").value(888.49))
                .andExpect(jsonPath("$.interesTotal").value(661.88))
                .andExpect(jsonPath("$.totalPagar").value(10661.88));
    }

    @Test
    @DisplayName("POST /api/v1/simulaciones/prestamo con monto invalido debe responder 400")
    void simularPrestamo_montoInvalido_badRequest() throws Exception {
        Map<String, Object> request = Map.of(
                "monto", 0,
                "tasaAnual", 12,
                "meses", 12
        );

        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.detalles.monto").value("El monto debe ser mayor que 0"));
    }
}