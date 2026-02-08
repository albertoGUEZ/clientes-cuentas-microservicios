package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository;

import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaBancariaJpaRepository extends JpaRepository<CuentaBancariaEntity, Long> {
}
