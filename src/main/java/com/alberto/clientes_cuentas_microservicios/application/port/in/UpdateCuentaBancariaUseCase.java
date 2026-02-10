package com.alberto.clientes_cuentas_microservicios.application.port.in;

import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;

public interface UpdateCuentaBancariaUseCase {

    CuentaBancaria updateTotal(Long cuentaBancariaId, double total);
}
