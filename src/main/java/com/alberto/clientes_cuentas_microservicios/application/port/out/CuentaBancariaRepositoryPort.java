package com.alberto.clientes_cuentas_microservicios.application.port.out;

import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;

import java.util.Optional;

public interface CuentaBancariaRepositoryPort {

    Optional<CuentaBancaria> findById(Long id);

    CuentaBancaria save(CuentaBancaria cuentaBancaria);
}
