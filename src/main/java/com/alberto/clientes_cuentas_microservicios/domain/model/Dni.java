package com.alberto.clientes_cuentas_microservicios.domain.model;

import java.util.Objects;

public final class Dni {

    private final String valor;

    public Dni(String valor) {
        if (!esValido(valor)) {
            throw new IllegalArgumentException("Formato de DNI inválido: debe ser 8 dígitos + letra");
        }
        this.valor = valor.toUpperCase();
    }

    private static boolean esValido(String valor) {
        return valor != null && valor.matches("\\d{8}[A-Z]");
    }

    public String valor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dni dni)) return false;
        return valor.equals(dni.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
