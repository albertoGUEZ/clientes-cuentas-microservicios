package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository;

import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaBancariaJpaRepository extends JpaRepository<CuentaBancariaEntity, Long> {

    List<CuentaBancariaEntity> findByDniCliente(String dniCliente);
}
