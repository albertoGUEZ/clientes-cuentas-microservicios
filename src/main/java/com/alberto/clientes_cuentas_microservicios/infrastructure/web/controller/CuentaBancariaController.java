package com.alberto.clientes_cuentas_microservicios.infrastructure.web.controller;

import com.alberto.clientes_cuentas_microservicios.application.port.in.CreateCuentaBancariaUseCase;
import com.alberto.clientes_cuentas_microservicios.application.port.in.UpdateCuentaBancariaUseCase;
import com.alberto.clientes_cuentas_microservicios.domain.model.CuentaBancaria;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.CreateCuentaBancariaRequest;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.CuentaBancariaResponse;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.UpdateCuentaBancariaRequest;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.mapper.CuentaBancariaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaBancariaController {

    private final CreateCuentaBancariaUseCase createCuentaBancariaUseCase;
    private final UpdateCuentaBancariaUseCase updateCuentaBancariaUseCase;
    private final CuentaBancariaDtoMapper cuentaBancariaDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaBancariaResponse createCuentaBancaria(
            @RequestBody CreateCuentaBancariaRequest request) {

        CuentaBancaria cuenta = createCuentaBancariaUseCase.createCuentaBancaria(
                new Dni(request.getDniCliente()),
                request.getTipoCuenta(),
                request.getTotal()
        );
        return cuentaBancariaDtoMapper.toResponse(cuenta);
    }

    @PutMapping("/{id}")
    public CuentaBancariaResponse updateCuentaBancaria(
            @PathVariable Long id,
            @RequestBody UpdateCuentaBancariaRequest request) {

        CuentaBancaria cuenta = updateCuentaBancariaUseCase.updateTotal(
                id,
                request.getTotal()
        );
        return cuentaBancariaDtoMapper.toResponse(cuenta);
    }
}
