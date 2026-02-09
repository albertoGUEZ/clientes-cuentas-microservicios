package com.alberto.clientes_cuentas_microservicios.infrastructure.web.controller;

import com.alberto.clientes_cuentas_microservicios.application.port.in.ClienteQueryUseCase;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.ClienteResponse;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.mapper.ClienteDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<ClienteResponse> getAllClientes() {
         return clienteDtoMapper.toResponseList(clienteQueryUseCase.getAll());
     }
}
