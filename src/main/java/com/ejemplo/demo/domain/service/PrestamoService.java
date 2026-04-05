package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    private static final int SCALE = 2;
    private static final MathContext MC = new MathContext(15, RoundingMode.HALF_UP);

    public PrestamoResponse simular(BigDecimal monto, BigDecimal tasaAnual, Integer meses) {
        if (monto == null || tasaAnual == null || meses == null) {
            throw new IllegalArgumentException("Todos los parametros son obligatorios");
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }

        if (tasaAnual.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La tasa anual debe ser mayor que 0");
        }

        if (meses <= 0) {
            throw new IllegalArgumentException("Los meses deben ser mayores que 0");
        }

        BigDecimal tasaMensual = tasaAnual
                .divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        double r = tasaMensual.doubleValue();
        int n = meses;
        double p = monto.doubleValue();

        double cuota = p * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);

        BigDecimal cuotaMensual = BigDecimal.valueOf(cuota).setScale(SCALE, RoundingMode.HALF_UP);
        BigDecimal totalPagar = cuotaMensual.multiply(BigDecimal.valueOf(meses), MC).setScale(SCALE, RoundingMode.HALF_UP);
        BigDecimal interesTotal = totalPagar.subtract(monto, MC).setScale(SCALE, RoundingMode.HALF_UP);

        return new PrestamoResponse(cuotaMensual, interesTotal, totalPagar);
    }
}