package com.alberto.clientes_cuentas_microservicios.application.port.in;

public interface UpdateCuentaBancariaUseCase {

    void updateTotal(Long cuentaBancariaId, double total);
}
