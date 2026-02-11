package com.alberto.clientes_cuentas_microservicios.application.port.out;

import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;

import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {

    List<Cliente> findAll();

    List<Cliente> findAllAdults();

    List<Cliente> findAllWithTotalBalanceGreaterThan(double total);

    Optional<Cliente> findByDni(Dni dni);

    Cliente save(Cliente cliente);
}
