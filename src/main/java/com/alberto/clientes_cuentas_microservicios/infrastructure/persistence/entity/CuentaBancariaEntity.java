package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity;

import com.alberto.clientes_cuentas_microservicios.domain.model.TipoCuenta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cuentas_bancarias")
@Getter
@Setter
@NoArgsConstructor
public class CuentaBancariaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "dni_cliente", nullable = false)
    private ClienteEntity cliente;
}
