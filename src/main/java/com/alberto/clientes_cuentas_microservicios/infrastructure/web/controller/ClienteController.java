package com.alberto.clientes_cuentas_microservicios.infrastructure.web.controller;

import com.alberto.clientes_cuentas_microservicios.application.port.in.ClienteQueryUseCase;
import com.alberto.clientes_cuentas_microservicios.domain.model.Dni;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.ClienteResponse;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.mapper.ClienteDtoMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
    })
    public List<ClienteResponse> getClientes() {
         return clienteDtoMapper.toResponseList(clienteQueryUseCase.getAll());
     }

    @GetMapping("/mayores-de-edad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes mayores de edad obtenida exitosamente"),
    })
    public List<ClienteResponse> getClientesMayoresDeEdad() {
        return clienteDtoMapper.toResponseList(clienteQueryUseCase.getAllAdults());
    }

    @GetMapping("/con-cuenta-superior-a/{cantidad}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes con cuenta superior a la cantidad obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Cantidad inválida")
    })
    public List<ClienteResponse> getClientesConCuentaSuperiorA(@PathVariable double cantidad) {
        return clienteDtoMapper.toResponseList(
                clienteQueryUseCase.getAllWithTotalBalanceGreaterThan(cantidad)
        );
    }

    @GetMapping("/{dni}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "DNI inválido")
    })
    public ClienteResponse getClientesPorDni(@PathVariable String dni) {
        return clienteDtoMapper.toResponse(
                clienteQueryUseCase.getByDni(new Dni(dni))
        );
    }
}
