package com.alberto.clientes_cuentas_microservicios.domain.exception;

public class InvalidDomainException extends RuntimeException {
    public InvalidDomainException(String message) {
        super(message);
    }
}
