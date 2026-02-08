package com.alberto.clientes_cuentas_microservicios.domain.model;

import java.util.Objects;

public final class Dni {

    private final String value;

    public Dni(String valor) {
        if (!isValid(valor)) {
            throw new IllegalArgumentException("Formato de DNI inválido: debe ser 8 dígitos + letra");
        }
        this.value = valor.toUpperCase();
    }

    private static boolean isValid(String valor) {
        return valor != null && valor.matches("\\d{8}[A-Z]");
    }

    public String valor() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dni dni)) return false;
        return value.equals(dni.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
