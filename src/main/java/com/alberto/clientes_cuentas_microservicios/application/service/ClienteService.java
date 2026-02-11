package com.alberto.clientes_cuentas_microservicios.application.service;

import com.alberto.clientes_cuentas_microservicios.application.port.in.ClienteQueryUseCase;
import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.exception.ClienteNotFoundException;
import com.alberto.clientes_cuentas_microservicios.domain.exception.InvalidDomainException;
import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return clienteRepository.findAllAdults();
    }

    @Override
    public List<Cliente> getAllWithTotalBalanceGreaterThan(double total) {
        if (total < 0) {
            throw new InvalidDomainException("El total no puede ser negativo");
        }
        return clienteRepository.findAllWithTotalBalanceGreaterThan(total);
    }



    @Override
    public Cliente getByDni(Dni dni) {
        return clienteRepository.findByDni(dni)
                .orElseThrow(() -> new ClienteNotFoundException(dni.toString()));
    }
}
