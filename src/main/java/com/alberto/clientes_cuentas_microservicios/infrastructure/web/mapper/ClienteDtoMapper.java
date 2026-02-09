package com.alberto.clientes_cuentas_microservicios.infrastructure.web.mapper;

import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.ClienteResponse;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.CuentaBancariaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteDtoMapper {

    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento", dateFormat = "dd/MM/yyyy")
    ClienteResponse toResponse(Cliente cliente);

    CuentaBancariaResponse toCuentaResponse(CuentaBancaria cuenta);

    List<ClienteResponse> toResponseList(List<Cliente> clientes);
    List<CuentaBancariaResponse> toCuentaResponseList(List<CuentaBancaria> cuentas);

    default String map(Dni dni) {
        return dni != null ? dni.value() : null;
    }
}
