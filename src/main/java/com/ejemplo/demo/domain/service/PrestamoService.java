package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.generated.model.PrestamoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class PrestamoService {

    private static final int SCALE = 2;
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL64;

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
                .divide(BigDecimal.valueOf(12), MATH_CONTEXT)
                .divide(BigDecimal.valueOf(100), MATH_CONTEXT);

        BigDecimal factor = BigDecimal.ONE.add(tasaMensual, MATH_CONTEXT).pow(meses, MATH_CONTEXT);
        BigDecimal numerador = monto.multiply(tasaMensual, MATH_CONTEXT).multiply(factor, MATH_CONTEXT);
        BigDecimal denominador = factor.subtract(BigDecimal.ONE, MATH_CONTEXT);

        BigDecimal cuotaMensual = numerador.divide(denominador, SCALE, RoundingMode.HALF_UP);
        BigDecimal totalPagar = cuotaMensual.multiply(BigDecimal.valueOf(meses));
        BigDecimal interesTotal = totalPagar.subtract(monto);

        PrestamoResponse response = new PrestamoResponse();
        response.setCuotaMensual(cuotaMensual);
        response.setTotalPagar(totalPagar);
        response.setInteresTotal(interesTotal);

        return response;
    }
}
