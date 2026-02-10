package com.alberto.clientes_cuentas_microservicios.application.service;

import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.application.port.out.CuentaBancariaRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.exception.CuentaBancariaNotFoundException;
import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.domain.model.TipoCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaBancariaServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepository;

    @Mock
    private CuentaBancariaRepositoryPort cuentaBancariaRepository;

    private CuentaBancariaService cuentaBancariaService;

    @BeforeEach
    void setUp() {
        cuentaBancariaService = new CuentaBancariaService(clienteRepository, cuentaBancariaRepository);
    }

    @Test
    void shouldCreateCuentaBancariaForExistingCliente() {
        Dni dni = new Dni("12345678A");
        TipoCuenta tipoCuenta = TipoCuenta.NORMAL;
        double total = 1000.0;

        Cliente clienteExistente = new Cliente(dni, "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1));
        CuentaBancaria cuentaGuardada = new CuentaBancaria(dni, tipoCuenta, total);
        cuentaGuardada.assignId(1L);

        when(clienteRepository.findByDni(dni)).thenReturn(Optional.of(clienteExistente));
        when(cuentaBancariaRepository.save(any(CuentaBancaria.class))).thenReturn(cuentaGuardada);

        CuentaBancaria resultado = cuentaBancariaService.createCuentaBancaria(dni, tipoCuenta, total);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getDniCliente()).isEqualTo(dni);
        assertThat(resultado.getTipoCuenta()).isEqualTo(tipoCuenta);
        assertThat(resultado.getTotal()).isEqualTo(total);

        verify(clienteRepository).findByDni(dni);
        verify(clienteRepository, never()).save(any());
        verify(cuentaBancariaRepository).save(argThat(cuenta ->
                cuenta.getDniCliente().equals(dni) &&
                        cuenta.getTipoCuenta().equals(tipoCuenta) &&
                        cuenta.getTotal().equals(total)
        ));
    }

    @Test
    void shouldCreateClienteAndCuentaBancariaWhenClienteDoesNotExist() {
        Dni dni = new Dni("99999999Z");
        TipoCuenta tipoCuenta = TipoCuenta.PREMIUM;
        double total = 5000.0;

        Cliente nuevoCliente = new Cliente(dni, "Nombre", "Apellido1", "Apellido2", LocalDate.now().minusYears(25));
        CuentaBancaria cuentaGuardada = new CuentaBancaria(dni, tipoCuenta, total);
        cuentaGuardada.assignId(2L);

        when(clienteRepository.findByDni(dni)).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(nuevoCliente);
        when(cuentaBancariaRepository.save(any(CuentaBancaria.class))).thenReturn(cuentaGuardada);

        CuentaBancaria resultado = cuentaBancariaService.createCuentaBancaria(dni, tipoCuenta, total);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(2L);
        assertThat(resultado.getDniCliente()).isEqualTo(dni);
        assertThat(resultado.getTipoCuenta()).isEqualTo(tipoCuenta);
        assertThat(resultado.getTotal()).isEqualTo(total);

        verify(clienteRepository).findByDni(dni);
        verify(clienteRepository).save(argThat(cliente -> cliente.getDni().equals(dni)));
        verify(cuentaBancariaRepository).save(any(CuentaBancaria.class));
    }

    @Test
    void shouldUpdateTotalWhenCuentaExists() {
        Long cuentaId = 1L;
        double nuevoTotal = 2500.0;
        Dni dni = new Dni("12345678A");

        CuentaBancaria cuentaExistente = new CuentaBancaria(dni, TipoCuenta.NORMAL, 1000.0);
        cuentaExistente.assignId(cuentaId);

        when(cuentaBancariaRepository.findById(cuentaId)).thenReturn(Optional.of(cuentaExistente));

        cuentaBancariaService.updateTotal(cuentaId, nuevoTotal);

        assertThat(cuentaExistente.getTotal()).isEqualTo(nuevoTotal);

        verify(cuentaBancariaRepository).findById(cuentaId);
        verify(cuentaBancariaRepository).save(cuentaExistente);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentCuenta() {
        Long cuentaId = 999L;
        double nuevoTotal = 2500.0;

        when(cuentaBancariaRepository.findById(cuentaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cuentaBancariaService.updateTotal(cuentaId, nuevoTotal))
                .isInstanceOf(CuentaBancariaNotFoundException.class);

        verify(cuentaBancariaRepository).findById(cuentaId);
        verify(cuentaBancariaRepository, never()).save(any());
    }
}
