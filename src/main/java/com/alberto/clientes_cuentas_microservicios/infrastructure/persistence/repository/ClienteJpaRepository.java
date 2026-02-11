package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.repository;

import com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, String> {

    @Query("SELECT cliente FROM ClienteEntity cliente " +
            "WHERE YEAR(CURRENT_DATE) - YEAR(cliente.fechaNacimiento) >= 18"
    )
    List<ClienteEntity> findAllAdults();

    @Query("SELECT cliente FROM ClienteEntity cliente " +
            "WHERE cliente.dni IN (" +
            "  SELECT cuenta.dniCliente FROM CuentaBancariaEntity cuenta " +
            "  GROUP BY cuenta.dniCliente " +
            "  HAVING SUM(cuenta.total) > :total" +
            ")"
    )
    List<ClienteEntity> findAllWithTotalBalanceGreaterThan(@Param("total") double total);

}
