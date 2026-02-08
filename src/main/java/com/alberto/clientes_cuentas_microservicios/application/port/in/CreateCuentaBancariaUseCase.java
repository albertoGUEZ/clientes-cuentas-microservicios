package com.alberto.clientes_cuentas_microservicios.application.port.in;

import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.domain.model.TipoCuenta;

public interface CreateCuentaBancariaUseCase {

    void createCuentaBancaria(Dni dni, TipoCuenta tipoCuenta, double total);
}
