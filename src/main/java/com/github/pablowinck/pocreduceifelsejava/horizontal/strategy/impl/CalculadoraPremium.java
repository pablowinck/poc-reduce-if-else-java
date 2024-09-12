package com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.impl;

import com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.CalculadoraDeTaxaStrategy;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraPremium implements CalculadoraDeTaxaStrategy {

    @Override
    public double calcular(double valor) {
        return valor * 0.2;
    }

    @Override
    public String getTipo() {
        return "PREMIUM";
    }

}
