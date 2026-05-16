package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.generated.api.DemoEstadoApi;
import com.ejemplo.demo.generated.model.EstadoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class DemoEstadoApiController implements DemoEstadoApi {

    private final AtomicInteger valorSingleton = new AtomicInteger();

    @Override
    public ResponseEntity<EstadoResponse> actualizarSingleton(Integer valor) {
        valorSingleton.set(valor);
        return ResponseEntity.ok(respuesta(EstadoResponse.TipoEnum.SINGLETON, valorSingleton.get()));
    }

    @Override
    public ResponseEntity<EstadoResponse> obtenerSingleton() {
        return ResponseEntity.ok(respuesta(EstadoResponse.TipoEnum.SINGLETON, valorSingleton.get()));
    }

    @Override
    public ResponseEntity<EstadoResponse> reiniciarSingleton() {
        valorSingleton.set(0);
        return ResponseEntity.ok(respuesta(EstadoResponse.TipoEnum.SINGLETON, valorSingleton.get()));
    }

    @Override
    public ResponseEntity<EstadoResponse> actualizarManual(Integer valor) {
        return ResponseEntity.ok(respuesta(EstadoResponse.TipoEnum.MANUAL, valor));
    }

    @Override
    public ResponseEntity<EstadoResponse> obtenerManual() {
        return ResponseEntity.ok(respuesta(EstadoResponse.TipoEnum.MANUAL, 0));
    }

    private EstadoResponse respuesta(EstadoResponse.TipoEnum tipo, Integer valor) {
        return new EstadoResponse()
                .tipo(tipo)
                .valorActual(valor);
    }
}
