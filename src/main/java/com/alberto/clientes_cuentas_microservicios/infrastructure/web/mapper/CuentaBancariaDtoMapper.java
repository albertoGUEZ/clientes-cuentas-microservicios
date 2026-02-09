package com.alberto.clientes_cuentas_microservicios.infrastructure.web.mapper;

import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.CuentaBancariaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaBancariaDtoMapper {

    @Mapping(source = "dniCliente", target = "dniCliente")
    CuentaBancariaResponse toResponse(CuentaBancaria cuenta);

    List<CuentaBancariaResponse> toResponseList(List<CuentaBancaria> cuentas);

    default String map(Dni dni) {
        return dni != null ? dni.value() : null;
    }
}
