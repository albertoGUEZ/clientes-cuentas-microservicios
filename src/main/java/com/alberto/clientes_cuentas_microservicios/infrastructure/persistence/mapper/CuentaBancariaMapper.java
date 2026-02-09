package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper;

import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaBancariaMapper {

    default CuentaBancaria toCuentaBancaria(CuentaBancariaEntity entity) {
        if (entity == null) return null;
        Dni dniCliente = entity.getDniCliente() != null
                ? new Dni(entity.getDniCliente())
                : null;

        return new CuentaBancaria(dniCliente, entity.getTipoCuenta(), entity.getTotal());
    }

    default CuentaBancariaEntity toCuentaBancariaEntity(CuentaBancaria cuenta) {
        if (cuenta == null) return null;

        CuentaBancariaEntity entity = new CuentaBancariaEntity();
        entity.setDniCliente(cuenta.getDniCliente().value()); // <-- usar getDniCliente()
        entity.setTipoCuenta(cuenta.getTipoCuenta());
        entity.setTotal(cuenta.getTotal());
        return entity;
    }
}
