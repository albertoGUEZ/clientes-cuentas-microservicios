package com.alberto.clientes_cuentas_microservicios.domain.model;

import com.alberto.clientes_cuentas_microservicios.domain.exception.InvalidDomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaBancariaTest {

    private Dni dni;

    @BeforeEach
    void setUp() {
        dni = new Dni("12345678A");
    }

    @Test
    void shouldCreateValidCuentaBancaria() {
        Double total = 100.0;

        CuentaBancaria cuenta = new CuentaBancaria(dni, TipoCuenta.NORMAL, total);

        assertNull(cuenta.getId());
        assertEquals(dni, cuenta.getDniCliente());
        assertEquals(TipoCuenta.NORMAL, cuenta.getTipoCuenta());
        assertEquals(total, cuenta.getTotal());
    }

    @Test
    void shouldRejectNullDni() {
        assertThrows(InvalidDomainException.class,
                () -> new CuentaBancaria(null, TipoCuenta.NORMAL, 100.0));
    }

    @Test
    void shouldRejectNullTipoCuenta() {
        assertThrows(InvalidDomainException.class,
                () -> new CuentaBancaria(dni, null, 100.0));
    }

    @Test
    void shouldRejectNullOrNegativeTotal() {
        assertThrows(InvalidDomainException.class,
                () -> new CuentaBancaria(dni, TipoCuenta.NORMAL, null));
        assertThrows(InvalidDomainException.class,
                () -> new CuentaBancaria(dni, TipoCuenta.NORMAL, -1.0));
    }

    @Test
    void shouldAllowZeroTotal() {
        assertDoesNotThrow(() -> new CuentaBancaria(dni, TipoCuenta.NORMAL, 0.0));
    }

    @Test
    void shouldAssignId() {
        CuentaBancaria cuenta = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);
        Long id = 1L;

        cuenta.assignId(id);

        assertEquals(id, cuenta.getId());
    }

    @Test
    void shouldNotAllowReassignId() {
        CuentaBancaria cuenta = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);
        cuenta.assignId(1L);

        assertThrows(InvalidDomainException.class, () -> cuenta.assignId(2L));
    }

    @Test
    void shouldUpdateTotal() {
        CuentaBancaria cuenta = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);
        Double newTotal = 200.0;

        cuenta.updateTotal(newTotal);

        assertEquals(newTotal, cuenta.getTotal());
    }

    @Test
    void shouldNotAllowInvalidTotalUpdate() {
        CuentaBancaria cuenta = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);

        assertThrows(InvalidDomainException.class, () -> cuenta.updateTotal(null));
        assertThrows(InvalidDomainException.class, () -> cuenta.updateTotal(-1.0));
    }

    @Test
    void shouldCompareById() {
        CuentaBancaria cuenta1 = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);
        CuentaBancaria cuenta2 = new CuentaBancaria(dni, TipoCuenta.PREMIUM, 200.0);

        cuenta1.assignId(1L);
        cuenta2.assignId(1L);

        assertEquals(cuenta1, cuenta2);
        assertEquals(cuenta1.hashCode(), cuenta2.hashCode());
    }
}
