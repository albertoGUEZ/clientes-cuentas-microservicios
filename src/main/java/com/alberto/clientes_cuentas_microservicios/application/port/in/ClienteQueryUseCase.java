package com.alberto.clientes_cuentas_microservicios.application.port.in;

import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;

import java.util.List;

public interface ClienteQueryUseCase {

    Cliente getByDni(Dni dni);

    List<Cliente> getAll();

    List<Cliente> getAllAdults();

    List<Cliente> getAllWithTotalBalanceGreaterThan(double total);
}
