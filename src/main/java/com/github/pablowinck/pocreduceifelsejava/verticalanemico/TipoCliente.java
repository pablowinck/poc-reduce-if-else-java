package com.github.pablowinck.pocreduceifelsejava.verticalanemico;

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
