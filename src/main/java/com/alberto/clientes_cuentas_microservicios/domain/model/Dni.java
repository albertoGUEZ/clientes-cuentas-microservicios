package com.alberto.clientes_cuentas_microservicios.domain.model;

import com.alberto.clientes_cuentas_microservicios.domain.exception.InvalidDomainException;

import java.util.Objects;

public final class Dni {

    private final String value;

    public Dni(String value) {
        if (!isValid(value)) {
            throw new InvalidDomainException("Formato de DNI inválido: debe ser 8 dígitos + letra");
        }
        this.value = value;
    }

    private static boolean isValid(String value) {
        return value != null && value.toUpperCase().matches("\\d{8}[A-Z]");
    }

    public String value() {
        return value;
    }

    public String getValue() {
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
