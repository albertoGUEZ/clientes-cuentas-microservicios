package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.adapter;

import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.model.Cliente;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.ClienteEntity;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper.ClienteMapper;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper.CuentaBancariaMapper;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository.ClienteJpaRepository;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository.CuentaBancariaJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteJpaAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository clienteRepository;
    private final CuentaBancariaJpaRepository cuentaRepository;
    private final ClienteMapper clienteMapper;
    private final CuentaBancariaMapper cuentaBancariaMapper;

    public ClienteJpaAdapter(
            ClienteJpaRepository clienteRepository,
            CuentaBancariaJpaRepository cuentaRepository,
            ClienteMapper clienteMapper,
            CuentaBancariaMapper cuentaBancariaMapper
    ) {
        this.clienteRepository = clienteRepository;
        this.cuentaRepository = cuentaRepository;
        this.clienteMapper = clienteMapper;
        this.cuentaBancariaMapper = cuentaBancariaMapper;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::mapToClienteWithCuentas)
                .toList();
    }

    @Override
    public Optional<Cliente> findByDni(Dni dni) {
        return clienteRepository.findById(dni.value())
                .map(this::mapToClienteWithCuentas);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {

        ClienteEntity entity = clienteMapper.toClienteEntity(cliente);
        clienteRepository.save(entity);

        cliente.getCuentas().forEach(cuenta -> cuentaRepository.save(
                cuentaBancariaMapper.toCuentaBancariaEntity(cuenta)
        ));

        return mapToClienteWithCuentas(entity);
    }


    private Cliente mapToClienteWithCuentas(ClienteEntity entity) {
        Cliente cliente = clienteMapper.toClient(entity);

        cuentaRepository.findByDniCliente(entity.getDni()).stream()
                .map(cuentaBancariaMapper::toCuentaBancaria)
                .forEach(cliente::addCuentaBancaria);

        return cliente;
    }
}
