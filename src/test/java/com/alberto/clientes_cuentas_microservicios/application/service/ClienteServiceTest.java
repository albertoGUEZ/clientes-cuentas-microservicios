package com.alberto.clientes_cuentas_microservicios.application.service;

import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.exception.ClienteNotFoundException;
import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
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
}
