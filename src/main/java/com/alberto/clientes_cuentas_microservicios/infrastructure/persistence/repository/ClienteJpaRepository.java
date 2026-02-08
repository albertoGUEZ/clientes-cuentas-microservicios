package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository;

import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, String> {
}
