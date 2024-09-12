package com.github.pablowinck.pocreduceifelsejava;

import com.github.pablowinck.pocreduceifelsejava.horizontal.CalculadoraDeTaxaHorizontal;
import com.github.pablowinck.pocreduceifelsejava.horizontal.CalculadoraDeTaxaHorizontalSpringComponent;
import com.github.pablowinck.pocreduceifelsejava.vertical.CalculadoraDeTaxa;
import com.github.pablowinck.pocreduceifelsejava.verticalanemico.CalculadoraDeTaxaAnemica;
import com.github.pablowinck.pocreduceifelsejava.verticalanemico.TipoCliente;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class PocReduceIfElseJavaApplication implements CommandLineRunner {

    private final CalculadoraDeTaxaHorizontalSpringComponent calculadoraDeTaxaHorizontalSpringComponent;

    public PocReduceIfElseJavaApplication(CalculadoraDeTaxaHorizontalSpringComponent calculadoraDeTaxaHorizontalSpringComponent) {
        this.calculadoraDeTaxaHorizontalSpringComponent = calculadoraDeTaxaHorizontalSpringComponent;
    }

    public static void main(String[] args) {
        SpringApplication.run(PocReduceIfElseJavaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Vertical
        var calculadora = new CalculadoraDeTaxa();
        log.info("[vertical] Taxa VIP: {}", calculadora.calcular("VIP", 100));
        log.info("[vertical] Taxa PREMIUM: {}", calculadora.calcular("PREMIUM", 100));
        log.info("[vertical] Taxa NORMAL: {}", calculadora.calcular("NORMAL", 100));
        // Vertical anêmico
        var calculadoraAnemica = new CalculadoraDeTaxaAnemica();
        log.info("[vertical anêmico] Taxa VIP: {}", calculadoraAnemica.calcular(TipoCliente.VIP, 100));
        log.info("[vertical anêmico] Taxa PREMIUM: {}", calculadoraAnemica.calcular(TipoCliente.PREMIUM, 100));
        log.info("[vertical anêmico] Taxa NORMAL: {}", calculadoraAnemica.calcular(TipoCliente.NORMAL, 100));
        // Horizontal
        var calculadoraHorizontal = new CalculadoraDeTaxaHorizontal();
        log.info("[horizontal] Taxa VIP: {}", calculadoraHorizontal.calcular("VIP", 100));
        log.info("[horizontal] Taxa PREMIUM: {}", calculadoraHorizontal.calcular("PREMIUM", 100));
        log.info("[horizontal] Taxa NORMAL: {}", calculadoraHorizontal.calcular("NORMAL", 100));
        // Horizontal com Spring Component
        log.info("[horizontal com Spring Component] Taxa VIP: {}", calculadoraDeTaxaHorizontalSpringComponent.calcular("VIP", 100));
        log.info("[horizontal com Spring Component] Taxa PREMIUM: {}", calculadoraDeTaxaHorizontalSpringComponent.calcular("PREMIUM", 100));
        log.info("[horizontal com Spring Component] Taxa NORMAL: {}", calculadoraDeTaxaHorizontalSpringComponent.calcular("NORMAL", 100));
    }

}
