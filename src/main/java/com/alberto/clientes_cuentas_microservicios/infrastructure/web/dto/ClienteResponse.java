package com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponse {

    private String dni;
    private String nombre;
    private String apellido1;
    private String apellido2;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fechaNacimiento;
    private List<CuentaBancariaResponse> cuentas;
}
