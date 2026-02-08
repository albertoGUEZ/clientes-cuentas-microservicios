package com.alberto.clientes_cuentas_microservicios.application.service;

import com.alberto.clientes_cuentas_microservicios.application.port.in.ClienteQueryUseCase;
import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;

import java.util.List;

public class ClienteService implements ClienteQueryUseCase {

    private final ClienteRepositoryPort clienteRepository;

    public ClienteService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> getAllAdults() {
        return clienteRepository.findAll()
                .stream()
                .filter(Cliente::isAdult)
                .toList();
    }


    @Override
    public List<Cliente> getAllWithTotalBalanceGreaterThan(double total) {
        if (total < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo");
        }

        return clienteRepository.findAll()
                .stream()
                .filter(cliente -> cliente.totalBalance() > total)
                .toList();
    }


    @Override
    public Cliente getByDni(Dni dni) {
        return clienteRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DNI: " + dni));
    }
}
