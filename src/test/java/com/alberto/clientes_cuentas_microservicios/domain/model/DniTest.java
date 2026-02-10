package com.alberto.clientes_cuentas_microservicios.domain.model;

import com.alberto.clientes_cuentas_microservicios.domain.exception.InvalidDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DniTest {

    @Test
    void shouldCreateValidDni() {
        String validDni = "12345678A";

        Dni dni = new Dni(validDni);

        assertEquals(validDni, dni.value());
        assertEquals(validDni, dni.getValue());
        assertEquals(validDni, dni.toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678A", "87654321B", "11111111Z"})
    void shouldAcceptValidDnis(String validDni) {
        assertDoesNotThrow(() -> new Dni(validDni));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234567A", "123456789A", "12345678", "ABCD1234A"})
    void shouldRejectInvalidDnis(String invalidDni) {
        assertThrows(InvalidDomainException.class, () -> new Dni(invalidDni));
    }

    @Test
    void shouldCompareEquals() {
        Dni dni1 = new Dni("12345678A");
        Dni dni2 = new Dni("12345678A");
        Dni dni3 = new Dni("87654321B");

        assertEquals(dni1, dni2);
        assertNotEquals(dni1, dni3);
        assertEquals(dni1.hashCode(), dni2.hashCode());
    }
}
