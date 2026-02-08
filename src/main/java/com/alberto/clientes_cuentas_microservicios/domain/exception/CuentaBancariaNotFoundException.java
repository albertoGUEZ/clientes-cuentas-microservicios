package com.alberto.clientes_cuentas_microservicios.domain.exception;

public class CuentaBancariaNotFoundException extends RuntimeException {
    public CuentaBancariaNotFoundException(Long id) {
        super("Cuenta bancaria no encontrada con ID: " + id);
    }
}
