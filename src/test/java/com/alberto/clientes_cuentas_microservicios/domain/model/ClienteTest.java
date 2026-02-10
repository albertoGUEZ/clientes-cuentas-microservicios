package com.alberto.clientes_cuentas_microservicios.domain.model;

import com.alberto.clientes_cuentas_microservicios.domain.exception.InvalidDomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Dni dni;
    private LocalDate fechaNacimiento;

    @BeforeEach
    void setUp() {
        dni = new Dni("12345678A");
        fechaNacimiento = LocalDate.of(1990, 1, 1);
    }

    @Test
    void shouldCreateValidCliente() {
        Cliente cliente = new Cliente(dni, "Juan", "Pérez", "García", fechaNacimiento);

        assertEquals(dni, cliente.getDni());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Pérez", cliente.getApellido1());
        assertEquals("García", cliente.getApellido2());
        assertEquals(fechaNacimiento, cliente.getFechaNacimiento());
        assertTrue(cliente.getCuentas().isEmpty());
    }

    @Test
    void shouldRejectNullDni() {
        assertThrows(InvalidDomainException.class,
                () -> new Cliente(null, "Juan", "Pérez", "García", fechaNacimiento));
    }

    @Test
    void shouldRejectNullNames() {
        assertThrows(InvalidDomainException.class,
                () -> new Cliente(dni, null, "Pérez", "García", fechaNacimiento));
        assertThrows(InvalidDomainException.class,
                () -> new Cliente(dni, "Juan", null, "García", fechaNacimiento));
        assertThrows(InvalidDomainException.class,
                () -> new Cliente(dni, "Juan", "Pérez", null, fechaNacimiento));
    }

    @Test
    void shouldRejectNullFechaNacimiento() {
        assertThrows(InvalidDomainException.class,
                () -> new Cliente(dni, "Juan", "Pérez", "García", null));
    }

    @Test
    void shouldDetermineIfAdult() {
        LocalDate fechaAdulto = LocalDate.now().minusYears(20);
        LocalDate fechaMenor = LocalDate.now().minusYears(15);

        Cliente adulto = new Cliente(dni, "Juan", "Pérez", "García", fechaAdulto);
        Cliente menor = new Cliente(new Dni("87654321B"), "Ana", "López", "Martín", fechaMenor);

        assertTrue(adulto.isAdult());
        assertFalse(menor.isAdult());
    }

    @Test
    void shouldAddValidCuentaBancaria() {
        Cliente cliente = new Cliente(dni, "Juan", "Pérez", "García", fechaNacimiento);
        CuentaBancaria cuenta = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);

        cliente.addCuentaBancaria(cuenta);

        assertEquals(1, cliente.getCuentas().size());
        assertTrue(cliente.getCuentas().contains(cuenta));
    }

    @Test
    void shouldNotAddCuentaFromDifferentClient() {
        Cliente cliente = new Cliente(dni, "Juan", "Pérez", "García", fechaNacimiento);
        Dni otroDni = new Dni("87654321B");
        CuentaBancaria cuenta = new CuentaBancaria(otroDni, TipoCuenta.NORMAL, 100.0);

        assertThrows(InvalidDomainException.class, () -> cliente.addCuentaBancaria(cuenta));
    }

    @Test
    void shouldCalculateTotalBalance() {
        Cliente cliente = new Cliente(dni, "Juan", "Pérez", "García", fechaNacimiento);
        CuentaBancaria cuenta1 = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);
        CuentaBancaria cuenta2 = new CuentaBancaria(dni, TipoCuenta.PREMIUM, 200.0);

        cliente.addCuentaBancaria(cuenta1);
        cliente.addCuentaBancaria(cuenta2);

        assertEquals(300.0, cliente.totalBalance());
    }

    @Test
    void shouldReturnImmutableCopyOfCuentas() {
        Cliente cliente = new Cliente(dni, "Juan", "Pérez", "García", fechaNacimiento);
        CuentaBancaria cuenta = new CuentaBancaria(dni, TipoCuenta.NORMAL, 100.0);
        cliente.addCuentaBancaria(cuenta);

        assertThrows(UnsupportedOperationException.class,
                () -> cliente.getCuentas().add(new CuentaBancaria(dni, TipoCuenta.PREMIUM, 200.0)));
    }

    @Test
    void shouldCompareByDni() {
        Cliente cliente1 = new Cliente(dni, "Juan", "Pérez", "García", fechaNacimiento);
        Cliente cliente2 = new Cliente(dni, "Ana", "López", "Martín", LocalDate.of(1985, 5, 5));
        Dni otroDni = new Dni("87654321B");
        Cliente cliente3 = new Cliente(otroDni, "Carlos", "Ruiz", "Sánchez", fechaNacimiento);

        assertEquals(cliente1, cliente2);
        assertNotEquals(cliente1, cliente3);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }
}
