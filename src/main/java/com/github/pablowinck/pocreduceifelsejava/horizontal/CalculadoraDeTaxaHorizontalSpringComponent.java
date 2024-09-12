package com.github.pablowinck.pocreduceifelsejava.horizontal;

import com.github.pablowinck.pocreduceifelsejava.horizontal.strategy.CalculadoraDeTaxaStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CalculadoraDeTaxaHorizontalSpringComponent {

    public Map<String, CalculadoraDeTaxaStrategy> strategies = new HashMap<>();

    private final ApplicationContext applicationContext;

    public CalculadoraDeTaxaHorizontalSpringComponent(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        applicationContext.getBeansOfType(CalculadoraDeTaxaStrategy.class)
                .values()
                .forEach(calculadoraDeTaxaStrategy -> strategies.put(calculadoraDeTaxaStrategy.getTipo(), calculadoraDeTaxaStrategy));
    }

    /**
     * Calcula a taxa conforme o tipo de cliente
     * Esse método ele cresce de maneira horizontal, ou seja, a cada novo tipo de cliente é necessário adicionar uma nova classe
     * Nos testes unitários temos um teste para cada classe, então a adição de um novo tipo de cliente não impacta nos testes existentes
     * A unica diferença deste método para o método da classe CalculadoraDeTaxaHorizontal é que ele adiciona os valores do Map com base no Spring
     * @param tipoCliente Tipo de cliente
     * @param valor Valor para calcular a taxa
     * @return Taxa calculada
     */
    public double calcular(String tipoCliente, double valor) {
        return strategies.get(tipoCliente).calcular(valor);
    }

}
