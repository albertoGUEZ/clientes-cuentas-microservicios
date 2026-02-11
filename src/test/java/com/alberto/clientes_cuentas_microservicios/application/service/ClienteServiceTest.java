package com.alberto.clientes_cuentas_microservicios.application.service;

import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.exception.ClienteNotFoundException;
import com.alberto.clientes_cuentas_microservicios.domain.exception.InvalidDomainException;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort clienteRepository;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService(clienteRepository);
    }

    @Test
    void shouldReturnAllClientes() {
        List<Cliente> expectedClientes = List.of(
                new Cliente(new Dni("12345678A"), "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1))
        );
        when(clienteRepository.findAll()).thenReturn(expectedClientes);

        List<Cliente> result = clienteService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getDni().value()).isEqualTo("12345678A");
    }

    @Test
    void shouldThrowExceptionWhenClienteNotFound() {
        Dni dni = new Dni("99999999Z");
        when(clienteRepository.findByDni(dni)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.getByDni(dni))
                .isInstanceOf(ClienteNotFoundException.class);
    }

    @Test
    void shouldReturnOnlyAdultClientes() {
        Cliente adulto1 = new Cliente(new Dni("11111111A"), "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1));
        Cliente adulto2 = new Cliente(new Dni("22222222B"), "Ana", "López", "Martín", LocalDate.of(1985, 5, 15));
        Cliente menor = new Cliente(new Dni("33333333C"), "Pedro", "Sánchez", "Ruiz", LocalDate.of(2010, 8, 20));

        when(clienteRepository.findAllAdults())
                .thenReturn(List.of(adulto1, adulto2));

        List<Cliente> result = clienteService.getAllAdults();

        assertThat(result).hasSize(2);
        verify(clienteRepository).findAllAdults();
    }

    @Test
    void shouldReturnEmptyListWhenNoAdults() {
        new Cliente(new Dni("11111111A"), "Pedro", "Sánchez", "Ruiz", LocalDate.of(2010, 1, 1));
        new Cliente(new Dni("22222222B"), "Luis", "García", "Martín", LocalDate.of(2015, 5, 15));

        List<Cliente> result = clienteService.getAllAdults();

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllClientesWhenAllAreAdults() {
        Cliente adulto1 = new Cliente(new Dni("11111111A"), "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1));
        Cliente adulto2 = new Cliente(new Dni("22222222B"), "Ana", "López", "Martín", LocalDate.of(1985, 5, 15));

        when(clienteRepository.findAllAdults())
                .thenReturn(List.of(adulto1, adulto2));

        List<Cliente> result = clienteService.getAllAdults();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnClientesWithTotalBalanceGreaterThan() {
        Cliente cliente1 = new Cliente(new Dni("11111111A"), "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1));
        Cliente cliente2 = new Cliente(new Dni("22222222B"), "Ana", "López", "Martín", LocalDate.of(1985, 5, 15));

        CuentaBancaria cuenta1 = new CuentaBancaria(new Dni("11111111A"), TipoCuenta.NORMAL, 1500.0);
        CuentaBancaria cuenta2 = new CuentaBancaria(new Dni("22222222B"), TipoCuenta.NORMAL, 500.0);

        when(clienteRepository.findAllWithTotalBalanceGreaterThan(10000))
                .thenReturn(List.of(cliente1));

        List<Cliente> result = clienteService.getAllWithTotalBalanceGreaterThan(10000);

        assertThat(result).hasSize(1);
        verify(clienteRepository).findAllWithTotalBalanceGreaterThan(10000);
    }

    @Test
    void shouldReturnEmptyListWhenNoClientesMeetBalanceThreshold() {
        new Cliente(new Dni("11111111A"), "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1));
        new Cliente(new Dni("22222222B"), "Ana", "López", "Martín", LocalDate.of(1985, 5, 15));

        List<Cliente> result = clienteService.getAllWithTotalBalanceGreaterThan(10000.0);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenTotalIsNegative() {
        assertThatThrownBy(() -> clienteService.getAllWithTotalBalanceGreaterThan(-100.0))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("El total no puede ser negativo");
    }

    @Test
    void shouldReturnAllClientesWhenAllMeetBalanceThreshold() {
        Cliente cliente1 = new Cliente(new Dni("11111111A"), "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1));
        Cliente cliente2 = new Cliente(new Dni("22222222B"), "Ana", "López", "Martín", LocalDate.of(1985, 5, 15));

        CuentaBancaria cuenta1 = new CuentaBancaria(new Dni("11111111A"), TipoCuenta.NORMAL, 100.0);
        CuentaBancaria cuenta2 = new CuentaBancaria(new Dni("22222222B"), TipoCuenta.NORMAL, 200.0);


        when(clienteRepository.findAllWithTotalBalanceGreaterThan(10000))
                .thenReturn(List.of(cliente1, cliente2));

        List<Cliente> result = clienteService.getAllWithTotalBalanceGreaterThan(10000);

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnClienteWhenFoundByDni() {
        Dni dni = new Dni("12345678A");
        Cliente expectedCliente = new Cliente(dni, "Juan", "Pérez", "García", LocalDate.of(1990, 1, 1));

        when(clienteRepository.findByDni(dni)).thenReturn(Optional.of(expectedCliente));

        Cliente result = clienteService.getByDni(dni);

        assertThat(result).isNotNull();
        assertThat(result.getDni()).isEqualTo(dni);
        assertThat(result.getNombre()).isEqualTo("Juan");
        assertThat(result.getApellido1()).isEqualTo("Pérez");
        assertThat(result.getApellido2()).isEqualTo("García");
        assertThat(result.getFechaNacimiento()).isEqualTo(LocalDate.of(1990, 1, 1));

        verify(clienteRepository).findByDni(dni);
    }

}
