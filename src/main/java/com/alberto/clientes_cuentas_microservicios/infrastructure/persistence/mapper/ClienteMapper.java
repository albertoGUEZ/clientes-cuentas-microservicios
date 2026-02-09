package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper;

import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "dni", source = "dni")
    Cliente toClient(ClienteEntity clienteEntity);

    @Mapping(target = "dni", source = "dni")
    ClienteEntity toClienteEntity(Cliente cliente);

    default String map(Dni dni) {
        return dni != null ? dni.value() : null;
    }

    default Dni map(String value) {
        return value != null ? new Dni(value) : null;
    }

    List<Cliente> toClienteList(List<ClienteEntity> clienteEntities);

    List<ClienteEntity> toClienteEntityList(List<Cliente> clientes);
}
