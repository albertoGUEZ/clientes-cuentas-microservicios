package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper;

import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaBancariaMapper {

    CuentaBancaria toCuentaBancaria(CuentaBancariaEntity cuentaBancariaEntity);

    CuentaBancariaEntity toCuentaBancariaEntity(CuentaBancaria cuentaBancaria);
}
