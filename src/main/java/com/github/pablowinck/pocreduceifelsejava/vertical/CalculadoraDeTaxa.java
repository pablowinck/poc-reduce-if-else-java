package com.github.pablowinck.pocreduceifelsejava.vertical;

public class CalculadoraDeTaxa {

    /**
     * Calcula a taxa de acordo com o tipo de cliente
     * Esse método ele cresce de maneira vertical, ou seja, a cada novo tipo de cliente é necessário adicionar um novo if
     * Impactando também em testes unitários, pois a cada novo tipo de cliente é necessário adicionar um novo teste
     * e pode ser quebrado algum teste existente
     *
     * @param tipoCliente Tipo de cliente
     * @param valor       Valor para calcular a taxa
     * @return Taxa calculada
     */
    public double calcular(String tipoCliente, double valor) {
        return switch (tipoCliente) {
            case "VIP" -> valor * 0.1;
            case "PREMIUM" -> valor * 0.2;
            case "NORMAL" -> valor * 0.3;
            default -> 0;
        };
    }

}
