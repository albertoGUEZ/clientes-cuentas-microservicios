package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.adapter;

import com.alberto.clientes_cuentas_microservicios.application.port.out.CuentaBancariaRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.CuentaBancariaEntity;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.mapper.CuentaBancariaMapper;
import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository.CuentaBancariaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CuentaBancariaJpaAdapter implements CuentaBancariaRepositoryPort {

    private final CuentaBancariaJpaRepository repository;
    private final CuentaBancariaMapper mapper;

    public CuentaBancariaJpaAdapter(CuentaBancariaJpaRepository repository, CuentaBancariaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<CuentaBancaria> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toCuentaBancaria);
    }

    @Override
    public CuentaBancaria save(CuentaBancaria cuentaBancaria) {
        CuentaBancariaEntity entity = mapper.toCuentaBancariaEntity(cuentaBancaria);
        CuentaBancariaEntity saved = repository.save(entity);
        return mapper.toCuentaBancaria(saved);
    }
}
