package com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.impl;

import com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.CalculadoraDeTaxaStrategy;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraNormal implements CalculadoraDeTaxaStrategy {

    @Override
    public double calcular(double valor) {
        return valor * 0.3;
    }

    @Override
    public String getTipo() {
        return "NORMAL";
    }

}
