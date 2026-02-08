package com.alberto.clientes_cuentas_microservicios.domain.factory;

import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;

import java.time.LocalDate;

public final class ClienteFactory {

    private ClienteFactory() {}

    public static Cliente createWithOnlyDni(Dni dni) {
        return new Cliente(
                dni,
                "PENDING",
                "PENDING",
                "PENDING",
                LocalDate.now()
        );
    }
}
