package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper;

import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CuentaBancariaMapper {

    default String map(Dni dni) {
        return dni != null ? dni.value() : null;
    }

    default Dni map(String value) {
        return value != null ? new Dni(value) : null;
    }

    @Mapping(target = "dniCliente", source = "dniCliente")
    CuentaBancariaEntity toCuentaBancariaEntity(CuentaBancaria cuenta);

    @AfterMapping
    default void setIdEntity(@MappingTarget CuentaBancariaEntity entity, CuentaBancaria cuenta) {
        if (cuenta != null) {
            entity.setId(cuenta.getId());
        }
    }

    @Mapping(target = "dniCliente", source = "dniCliente")
    CuentaBancaria toCuentaBancaria(CuentaBancariaEntity entity);

    @AfterMapping
    default void setId(@MappingTarget CuentaBancaria cuenta, CuentaBancariaEntity entity) {
        if (entity != null && entity.getId() != null) {
            cuenta.assignId(entity.getId());
        }
    }
}




