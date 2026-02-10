package com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCuentaBancariaRequest {
    private Double total;
}
