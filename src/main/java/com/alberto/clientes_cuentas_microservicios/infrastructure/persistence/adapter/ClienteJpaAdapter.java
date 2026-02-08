package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.adapter;

import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.ClienteEntity;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper.ClienteMapper;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository.ClienteJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteJpaAdapter implements ClienteRepositoryPort {
    private final ClienteJpaRepository repository;
    private final ClienteMapper clienteMapper;

    public ClienteJpaAdapter(ClienteJpaRepository repository, ClienteMapper clienteMapper) {
        this.repository = repository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteMapper.toClienteList(repository.findAll());
    }

    @Override
    public Optional<Cliente> findByDni(Dni dni) {
        return repository.findById(dni.value())
                .map(clienteMapper::toClient);
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = clienteMapper.toClienteEntity(cliente);
        ClienteEntity saved = repository.save(entity);
        return clienteMapper.toClient(saved);
    }
}
