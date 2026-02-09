package com.alberto.clientes_cuentas_microservicios.infrastructure.web.controller;

import com.alberto.clientes_cuentas_microservicios.application.port.in.ClienteQueryUseCase;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.ClienteResponse;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.mapper.ClienteDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteQueryUseCase clienteQueryUseCase;
    private final ClienteDtoMapper clienteDtoMapper;

     @GetMapping
    public List<ClienteResponse> getClientes() {
         return clienteDtoMapper.toResponseList(clienteQueryUseCase.getAll());
     }

    @GetMapping("/mayores-de-edad")
    public List<ClienteResponse> getClientesMayoresDeEdad() {
        return clienteDtoMapper.toResponseList(clienteQueryUseCase.getAllAdults());
    }

    @GetMapping("/con-cuenta-superior-a/{cantidad}")
    public List<ClienteResponse> getClientesConCuentaSuperiorA(@PathVariable double cantidad) {
        return clienteDtoMapper.toResponseList(
                clienteQueryUseCase.getAllWithTotalBalanceGreaterThan(cantidad)
        );
    }
}
