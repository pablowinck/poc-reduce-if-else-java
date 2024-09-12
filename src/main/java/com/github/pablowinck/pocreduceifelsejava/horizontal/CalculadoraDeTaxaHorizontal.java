package com.github.pablowinck.pocreduceifelsejava.horizontal;

import com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.CalculadoraDeTaxaStrategy;
import com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.impl.CalculadoraNormal;
import com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.impl.CalculadoraPremium;
import com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.impl.CalculadoraVIP;

import java.util.HashMap;
import java.util.Map;

public class CalculadoraDeTaxaHorizontal {

    public static final Map<String, CalculadoraDeTaxaStrategy> strategies = new HashMap<>();

    static {
        strategies.put("VIP", new CalculadoraVIP());
        strategies.put("PREMIUM", new CalculadoraPremium());
        strategies.put("NORMAL", new CalculadoraNormal());
    }

    /**
     * Calcula a taxa conforme o tipo de cliente
     * Esse método ele cresce de maneira horizontal, ou seja, a cada novo tipo de cliente é necessário adicionar uma nova classe
     * Nos testes unitários temos um teste para cada classe, então a adição de um novo tipo de cliente não impacta nos testes existentes
     * @param tipoCliente Tipo de cliente
     * @param valor Valor para calcular a taxa
     * @return Taxa calculada
     */
    public double calcular(String tipoCliente, double valor) {
        return strategies.get(tipoCliente).calcular(valor);
    }

}
