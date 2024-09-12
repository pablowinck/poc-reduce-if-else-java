# Projeto: POC Reduce If-Else Java

## Visão Geral

Este projeto visa demonstrar abordagens para a eliminação de estruturas `if-else` no cálculo de taxas para diferentes
tipos de clientes em Java. Através de diferentes estratégias, o projeto apresenta formas de lidar com a adição de novos
tipos de clientes sem impactar significativamente o código existente.

Projeto inspirado no
artigo: [Stop using if-else statements in Java](https://medium.com/javarevisited/stop-using-if-else-statements-in-java-57234e13bf9d)

Três abordagens principais são apresentadas:

- **Vertical (switch/if-else)**: A abordagem tradicional de crescimento vertical onde novos tipos de clientes são
  adicionados diretamente em condicionais.
- **Vertical Anêmica (Enum)**: Uso de um `enum` com comportamentos diferentes para cada tipo de cliente, mantendo a
  lógica centralizada em um único local.
- **Horizontal (Strategy Pattern)**: Implementação baseada no padrão de design Strategy, onde a lógica de cálculo de
  taxas é distribuída entre diferentes classes, facilitando a escalabilidade sem impactar o código existente.

## Estrutura do Projeto

### Dependências (pom.xml)

O projeto utiliza o **Spring Boot** como base, junto com o **Lombok** para simplificar o código e reduzir a verbosidade.
As dependências incluem:

- `spring-boot-starter`: A dependência principal para rodar o projeto Spring Boot.
- `lombok`: Para evitar boilerplate de código.
- `spring-boot-starter-test`: Para facilitar a implementação e execução de testes unitários.

### Abordagens

#### 1. Vertical (if-else / switch)

Na classe `CalculadoraDeTaxa`, o cálculo de taxas é realizado utilizando a estrutura `switch`. A cada novo tipo de
cliente, é necessário adicionar um novo `case`, o que aumenta o acoplamento da lógica e dos testes, tornando a
manutenção mais complexa com o tempo.

```java
public class CalculadoraDeTaxa {

    public double calcular(String tipoCliente, double valor) {
        return switch (tipoCliente) {
            case "VIP" -> valor * 0.1;
            case "PREMIUM" -> valor * 0.2;
            case "NORMAL" -> valor * 0.3;
            default -> 0;
        };
    }

}
```

Utilização:

```java
var calculadora = new CalculadoraDeTaxa();
var valor = 100;
var taxa = calculadora.calcular("VIP", valor);
```

#### 2. Vertical Anêmica (Enum)

A classe `CalculadoraDeTaxaAnemica` utiliza um `enum` para representar os diferentes tipos de clientes. Cada tipo de
cliente tem sua própria lógica de cálculo de taxa encapsulada no próprio `enum`. Isso evita a repetição de condicionais
no código.

Enum:

```java
public enum TipoCliente {

    VIP {
        @Override
        public double calcular(double valor) {
            return valor * 0.1;
        }
    },
    PREMIUM {
        @Override
        public double calcular(double valor) {
            return valor * 0.2;
        }
    },
    NORMAL {
        @Override
        public double calcular(double valor) {
            return valor * 0.3;
        }
    };

    public abstract double calcular(double valor);

}
```

Calculadora:

```java
public class CalculadoraDeTaxaAnemica {

    public double calcular(TipoCliente tipoCliente, double valor) {
        return tipoCliente.calcular(valor);
    }

}
```

Utilização:

```java
var calculadora = new CalculadoraDeTaxaAnemica();
var valor = 100;
var taxa = calculadora.calcular(TipoCliente.VIP, valor);
```

#### 3. Horizontal (Strategy Pattern)

A implementação horizontal adota o padrão Strategy, onde cada tipo de cliente tem sua própria classe de cálculo de taxa.
Isso facilita a adição de novos tipos de clientes sem modificar as classes existentes, seguindo o princípio
aberto/fechado (Open/Closed Principle) do SOLID.

##### Sem Spring

Com isso, criamos classes concretas que implementam uma interface comum, `CalculadoraDeTaxaStrategy`, e utilizamos um mapa para associar cada tipo de cliente a sua respectiva classe de cálculo.

Interface:
```java
public interface CalculadoraDeTaxaStrategy {

    double calcular(double valor);

}
```

Exemplo de implementação:
```java
public class CalculadoraNormal implements CalculadoraDeTaxaStrategy {

    @Override
    public double calcular(double valor) {
        return valor * 0.3;
    }
  
}
```

Exemplo de mapa de estratégias:
```java
public class CalculadoraDeTaxaHorizontal {

    public static final Map<String, CalculadoraDeTaxaStrategy> strategies = new HashMap<>();

    static {
        strategies.put("VIP", new CalculadoraVIP());
        strategies.put("PREMIUM", new CalculadoraPremium());
        strategies.put("NORMAL", new CalculadoraNormal());
    }

    public double calcular(String tipoCliente, double valor) {
        return strategies.get(tipoCliente).calcular(valor);
    }

}
```

Utilização:

```java
var calculadora = new CalculadoraDeTaxaHorizontal();
var valor = 100;
var taxa = calculadora.calcular("VIP", valor);
```

##### Com Spring

Com o uso de Spring, as classes de cálculo são componentes que são gerenciados pelo framework, facilitando a injeção de
dependências e a configuração automática das estratégias.

Porém, vamos precisar modificar um pouco as strategies para que o Spring consiga identificar e injetar as dependências.

Interface:
```java
public interface CalculadoraDeTaxaStrategy {

    double calcular(double valor);

    // Método para identificar o tipo da estratégia, 
    // necessário para montarmos o mapa de estratégias posteriormente
    String getTipo();
    
}
```

Exemplo de implementação:
```java
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
```

Componente que gerencia as estratégias:

```java

@Component
public class CalculadoraDeTaxaHorizontalSpringComponent {

    private final Map<String, CalculadoraDeTaxaStrategy> strategies = new HashMap<>();

  /**
   * Inicializa o componente com todas as estratégias disponíveis no contexto do Spring.
   * @PostConstruct é uma anotação do Spring que indica que o método deve ser executado após a inicialização do bean.
   * Isso garante que todas as estratégias estejam disponíveis antes de serem utilizadas.
   */
  @PostConstruct
    public void init() {
        applicationContext.getBeansOfType(CalculadoraDeTaxaStrategy.class)
                .values()
                .forEach(calculadoraDeTaxaStrategy -> strategies.put(calculadoraDeTaxaStrategy.getTipo(), calculadoraDeTaxaStrategy));
    }

    public double calcular(String tipoCliente, double valor) {
        return strategies.get(tipoCliente).calcular(valor);
    }

}
```

Utilização:

```java
import org.springframework.stereotype.Component;

@Component
public class Example {

    private final CalculadoraDeTaxaHorizontalSpringComponent calculadoraDeTaxaHorizontalSpringComponent;

    public Example(CalculadoraDeTaxaHorizontalSpringComponent calculadoraDeTaxaHorizontalSpringComponent) {
        this.calculadoraDeTaxaHorizontalSpringComponent = calculadoraDeTaxaHorizontalSpringComponent;
    }

    public void example() {
        var valor = 100;
        var taxa = calculadoraDeTaxaHorizontalSpringComponent.calcular("VIP", valor);
    }

}
```

### Testes Unitários

O projeto
contém uma
cobertura de
testes unitários
que validam
cada uma
das abordagens
descritas,
garantindo que
as taxas
para os
diferentes tipos
de clientes
sejam calculadas
corretamente .

Exemplo de
teste para
a abordagem
vertical:

```java

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
```

### Conclusão

O projeto `poc-reduce-if-else-java` explora diversas maneiras de reduzir o uso de estruturas `if-else` em Java. A abordagem mais adequada depende das necessidades do projeto, sendo que o uso do padrão Strategy (horizontal) é recomendado para cenários que exigem escalabilidade e flexibilidade.

### Como Executar

Para executar o projeto, certifique-se de ter a jdk 17 instalada e utilize o comando Maven:

```bash
mvn spring-boot:run
```

Isso iniciará a aplicação e exibirá os cálculos de taxas para os diferentes tipos de clientes diretamente no console.

Ou rode os testes unitários com o comando:

```bash
mvn test
```