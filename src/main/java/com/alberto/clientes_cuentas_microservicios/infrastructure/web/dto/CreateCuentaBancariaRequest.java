package com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto;

import com.alberto.clientes_cuentas_microservicios.domain.model.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCuentaBancariaRequest {

    private String dniCliente;
    private TipoCuenta tipoCuenta;
    private Double total;
}
