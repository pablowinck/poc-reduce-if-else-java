package com.github.pablowinck.pocreduceifelsejava;

import com.github.pablowinck.pocreduceifelsejava.horizontal.CalculadoraDeTaxaHorizontal;
import com.github.pablowinck.pocreduceifelsejava.horizontal.CalculadoraDeTaxaHorizontalSpringComponent;
import com.github.pablowinck.pocreduceifelsejava.vertical.CalculadoraDeTaxa;
import com.github.pablowinck.pocreduceifelsejava.verticalanemico.CalculadoraDeTaxaAnemica;
import com.github.pablowinck.pocreduceifelsejava.verticalanemico.TipoCliente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PocReduceIfElseJavaApplicationTests {

    @Autowired
    private CalculadoraDeTaxaHorizontalSpringComponent calculadoraDeTaxaHorizontalSpringComponent;

    @ParameterizedTest(name = "[vertical] {0} -> {1}")
    @DisplayName("[vertical] Deve calcular as taxas corretamente")
    @CsvSource({
            "VIP, 10",
            "PREMIUM, 20",
            "NORMAL, 30"})
    void deveCalcularAsTaxasCorretamente(String tipoCliente, double valorEsperado) {
        var calculadora = new CalculadoraDeTaxa();
        var valor = 100;
        var taxa = calculadora.calcular(tipoCliente, valor);
        assertEquals(valorEsperado, taxa);
    }

    @ParameterizedTest(name = "[vertical anemico] {0} -> {1}")
    @DisplayName("[vertical anemico] Deve calcular as taxas corretamente")
    @CsvSource({
            "VIP, 10",
            "PREMIUM, 20",
            "NORMAL, 30"})
    void deveCalcularAsTaxasCorretamenteAnemico(String tipoCliente, double valorEsperado) {
        var calculadora = new CalculadoraDeTaxaAnemica();
        var valor = 100;
        var taxa = calculadora.calcular(TipoCliente.valueOf(tipoCliente), valor);
        assertEquals(valorEsperado, taxa);
    }

    @ParameterizedTest(name = "[horizontal] {0} -> {1}")
    @DisplayName("[horizontal] Deve calcular as taxas corretamente")
    @CsvSource({
            "VIP, 10",
            "PREMIUM, 20",
            "NORMAL, 30"})
    void deveCalcularAsTaxasCorretamenteHorizontal(String tipoCliente, double valorEsperado) {
        var calculadora = new CalculadoraDeTaxaHorizontal();
        var valor = 100;
        var taxa = calculadora.calcular(tipoCliente, valor);
        assertEquals(valorEsperado, taxa);
    }

    @ParameterizedTest(name = "[horizontal com Spring Component] {0} -> {1}")
    @DisplayName("[horizontal com Spring Component] Deve calcular as taxas corretamente")
    @CsvSource({
            "VIP, 10",
            "PREMIUM, 20",
            "NORMAL, 30"})
    void deveCalcularAsTaxasCorretamenteHorizontalSpringComponent(String tipoCliente, double valorEsperado) {
        var valor = 100;
        var taxa = calculadoraDeTaxaHorizontalSpringComponent.calcular(tipoCliente, valor);
        assertEquals(valorEsperado, taxa);
    }

}
