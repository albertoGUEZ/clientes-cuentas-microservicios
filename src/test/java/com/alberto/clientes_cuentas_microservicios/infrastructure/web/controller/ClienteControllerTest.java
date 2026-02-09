package com.alberto.clientes_cuentas_microservicios.infrastructure.web.controller;

import com.alberto.clientes_cuentas_microservicios.infrastructure.web.dto.ClienteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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



}
