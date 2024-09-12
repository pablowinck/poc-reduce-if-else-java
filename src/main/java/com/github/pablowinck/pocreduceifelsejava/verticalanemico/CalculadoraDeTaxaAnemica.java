package com.github.pablowinck.pocreduceifelsejava.verticalanemico;

public class CalculadoraDeTaxaAnemica {

    /**
     * Método que calcula a taxa conforme o tipo de cliente
     * Esse método ele cresce de maneira vertical, ou seja, a cada novo tipo de cliente é necessário adicionar um novo valor no enum
     * Impactando também em testes unitários, pois a cada novo tipo de cliente é necessário adicionar um novo teste neste método
     * @param tipoCliente Tipo de cliente
     * @param valor Valor para calcular a taxa
     * @return Taxa calculada
     */
    public double calcular(TipoCliente tipoCliente, double valor) {
        return tipoCliente.calcular(valor);
    }

}
