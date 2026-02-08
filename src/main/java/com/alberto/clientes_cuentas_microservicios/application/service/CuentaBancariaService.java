package com.alberto.clientes_cuentas_microservicios.application.service;

import com.alberto.clientes_cuentas_microservicios.application.port.in.CreateCuentaBancariaUseCase;
import com.alberto.clientes_cuentas_microservicios.application.port.in.UpdateCuentaBancariaUseCase;
import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.application.port.out.CuentaBancariaRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.exception.CuentaBancariaNotFoundException;
import com.alberto.clientes_cuentas_microservicios.domain.factory.ClienteFactory;
import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.domain.model.TipoCuenta;

public class CuentaBancariaService implements CreateCuentaBancariaUseCase, UpdateCuentaBancariaUseCase {

    private final ClienteRepositoryPort clienteRepository;
    private final CuentaBancariaRepositoryPort cuentaBancariaRepository;

    public CuentaBancariaService(ClienteRepositoryPort clienteRepository, CuentaBancariaRepositoryPort cuentaBancariaRepository, ClienteFactory clienteFactory) {
        this.clienteRepository = clienteRepository;
        this.cuentaBancariaRepository = cuentaBancariaRepository;
    }

    @Override
    public void createCuentaBancaria(Dni dni, TipoCuenta tipoCuenta, double total) {

        // Business requirement: if the client does not exist, it must be created automatically
        Cliente cliente = clienteRepository.findByDni(dni)
                .orElseGet(() -> clienteRepository.save(
                        ClienteFactory.createWithOnlyDni(dni)
                ));

        CuentaBancaria cuenta = new CuentaBancaria(
                dni,
                tipoCuenta,
                total
        );
        cliente.addCuentaBancaria(cuenta);
        cuentaBancariaRepository.save(cuenta);
        clienteRepository.save(cliente);
    }

    @Override
    public void updateTotal(Long cuentaBancariaId, double newTotal) {
        CuentaBancaria cuenta = cuentaBancariaRepository.findById(cuentaBancariaId)
                .orElseThrow(() ->
                        new CuentaBancariaNotFoundException(cuentaBancariaId)
                );

        cuenta.updateTotal(newTotal);
        cuentaBancariaRepository.save(cuenta);

    }
}
