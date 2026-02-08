package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper;

import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.ClienteEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toClient(ClienteEntity clienteEntity);

    ClienteEntity toClienteEntity(Cliente cliente);

    List<Cliente> toClienteList(List<ClienteEntity> clienteEntities);

    List<ClienteEntity> toClienteEntityList(List<Cliente> clientes);
}
