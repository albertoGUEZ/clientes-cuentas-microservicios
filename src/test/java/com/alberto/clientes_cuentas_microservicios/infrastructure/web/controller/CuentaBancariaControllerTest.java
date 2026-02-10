package com.alberto.clientes_cuentas_microservicios.infrastructure.web.controller;

import com.alberto.clientes_cuentas_microservicios.domain.model.TipoCuenta;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.CreateCuentaBancariaRequest;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.CuentaBancariaResponse;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.UpdateCuentaBancariaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
class CuentaBancariaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCuentaBancariaForExistingCliente() throws Exception {
        CreateCuentaBancariaRequest request = new CreateCuentaBancariaRequest(
                "11111111A",
                TipoCuenta.PREMIUM,
                3000.0
        );

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.dniCliente").value("11111111A"))
                .andExpect(jsonPath("$.tipoCuenta").value("PREMIUM"))
                .andExpect(jsonPath("$.total").value(3000.0));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.dni=='11111111A')].cuentas").exists())
                .andExpect(jsonPath("$[?(@.dni=='11111111A')].cuentas[?(@.tipoCuenta=='PREMIUM' && @.total==3000.0)]").exists());
    }

    @Test
    void shouldCreateCuentaBancariaForNewCliente() throws Exception {
        CreateCuentaBancariaRequest request = new CreateCuentaBancariaRequest(
                "99999999Z",
                TipoCuenta.NORMAL,
                1500.0
        );

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.dniCliente").value("99999999Z"))
                .andExpect(jsonPath("$.tipoCuenta").value("NORMAL"))
                .andExpect(jsonPath("$.total").value(1500.0));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.dni=='99999999Z')]").exists())
                .andExpect(jsonPath("$[?(@.dni=='99999999Z')].nombre").value("PENDING"))
                .andExpect(jsonPath("$[?(@.dni=='99999999Z')].apellido1").value("PENDING"))
                .andExpect(jsonPath("$[?(@.dni=='99999999Z')].apellido2").value("PENDING"))
                .andExpect(jsonPath("$[?(@.dni=='99999999Z')].cuentas[0].tipoCuenta").value("NORMAL"))
                .andExpect(jsonPath("$[?(@.dni=='99999999Z')].cuentas[0].total").value(1500.0));
    }

    @Test
    void shouldUpdateCuentaBancariaTotal() throws Exception {
        CreateCuentaBancariaRequest createRequest = new CreateCuentaBancariaRequest(
                "11111111A", TipoCuenta.NORMAL, 1000.0
        );

        String createResponse = mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CuentaBancariaResponse createdCuenta = objectMapper.readValue(createResponse, CuentaBancariaResponse.class);
        Long cuentaId = createdCuenta.getId();

        UpdateCuentaBancariaRequest updateRequest = new UpdateCuentaBancariaRequest(2500.0);

        mockMvc.perform(put("/cuentas/{id}", cuentaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(cuentaId))
                .andExpect(jsonPath("$.dniCliente").value("11111111A"))
                .andExpect(jsonPath("$.tipoCuenta").value("NORMAL"))
                .andExpect(jsonPath("$.total").value(2500.0));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentCuenta() throws Exception {
        Long cuentaIdInexistente = 999L;
        UpdateCuentaBancariaRequest updateRequest = new UpdateCuentaBancariaRequest(1500.0);

        mockMvc.perform(put("/cuentas/{id}", cuentaIdInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }


}
