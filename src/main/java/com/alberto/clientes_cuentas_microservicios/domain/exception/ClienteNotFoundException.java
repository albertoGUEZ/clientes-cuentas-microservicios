package com.alberto.clientes_cuentas_microservicios.domain.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(String dni) {
        super("Cliente no encontrado con DNI: " + dni);
    }
}
