package com.alberto.clientes_cuentas_microservicios.infrastructure.web.controller;

import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.ClienteResponse;
import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.CuentaBancariaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnFiveInitialClientes() throws Exception {
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].dni").isString())
                .andExpect(jsonPath("$[1].dni").isString())
                .andExpect(jsonPath("$[2].dni").isString())
                .andExpect(jsonPath("$[3].dni").isString())
                .andExpect(jsonPath("$[4].dni").isString())
                .andExpect(jsonPath("$[*].dni").value(hasItems("11111111A", "22222222B", "33333333C", "44444444D", "55555555E")));
    }

    @Test
    void shouldReturnOnlyAdultClientes() throws Exception {
        LocalDate fechaLimite = LocalDate.now().minusYears(18);

        ClienteResponse[] clientes = mockMvc.perform(get("/clientes/mayores-de-edad"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .transform(this::toClientes);

        assertThat(clientes)
                .hasSize(4)
                .allMatch(c -> c.getFechaNacimiento().isBefore(fechaLimite),
                        "todos los clientes deben ser mayores de edad");
    }

    private ClienteResponse[] toClientes(String json) {
        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .readValue(json, ClienteResponse[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void shouldReturnClientesWithAccountBalanceGreaterThan() throws Exception {
        mockMvc.perform(get("/clientes/con-cuenta-superior-a/1000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].dni").isString())
                .andExpect(jsonPath("$[0].cuentas").exists())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());

                    ClienteResponse[] clientes = mapper.readValue(jsonResponse, ClienteResponse[].class);

                    for (ClienteResponse cliente : clientes) {
                        double totalBalance = cliente.getCuentas().stream()
                                .mapToDouble(CuentaBancariaResponse::getTotal)
                                .sum();

                        assertThat(totalBalance)
                                .isGreaterThan(1000.0)
                                .withFailMessage("Cliente %s tiene balance %.2f, debe ser > 1000",
                                        cliente.getDni(), totalBalance);
                    }
                });
    }
}
