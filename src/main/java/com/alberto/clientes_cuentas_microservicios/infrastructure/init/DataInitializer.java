package com.alberto.clientes_cuentas_microservicios.infrastructure.init;

import com.alberto.clientes_cuentas_microservicios.application.port.out.ClienteRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.application.port.out.CuentaBancariaRepositoryPort;
import com.alberto.clientes_cuentas_microservicios.domain.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.alberto.clientes_cuentas_microservicios.domain.factory.ClienteFactory;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepositoryPort clienteRepository;

    public DataInitializer(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        Cliente cliente1 = ClienteFactory.createWithAllData(
                new Dni("11111111A"),
                "Juan",
                "Pérez",
                "López",
                LocalDate.of(1959, 9, 12)
        );
        cliente1.addCuentaBancaria(new CuentaBancaria(cliente1.getDni(), TipoCuenta.PREMIUM, 150000.0));
        cliente1.addCuentaBancaria(new CuentaBancaria(cliente1.getDni(), TipoCuenta.NORMAL, 20000.0));
        clienteRepository.save(cliente1);

        Cliente cliente2 = ClienteFactory.createWithAllData(
                new Dni("22222222B"),
                "Raúl",
                "Canales",
                "Rodríguez",
                LocalDate.of(1985, 3, 1)
        );
        cliente2.addCuentaBancaria(new CuentaBancaria(cliente2.getDni(), TipoCuenta.NORMAL, 50000.0));
        cliente2.addCuentaBancaria(new CuentaBancaria(cliente2.getDni(), TipoCuenta.JUNIOR, 300.0));
        clienteRepository.save(cliente2);

        Cliente cliente3 = ClienteFactory.createWithAllData(
                new Dni("33333333C"),
                "Elena",
                "Ruiz",
                "Herrera",
                LocalDate.of(2010, 5, 10)
        );
        cliente3.addCuentaBancaria(new CuentaBancaria(cliente3.getDni(), TipoCuenta.JUNIOR, 300.0));
        clienteRepository.save(cliente3);

        Cliente cliente4 = ClienteFactory.createWithAllData(
                new Dni("44444444D"),
                "Raquel",
                "Ruiz",
                "Herrera",
                LocalDate.of(2002, 6, 21)
        );
        cliente4.addCuentaBancaria(new CuentaBancaria(cliente4.getDni(), TipoCuenta.NORMAL, 75000.0));
        clienteRepository.save(cliente4);

        Cliente cliente5 = ClienteFactory.createWithAllData(
                new Dni("55555555E"),
                "María",
                "Sánchez",
                "Torres",
                LocalDate.of(1999, 8, 8)
        );
        cliente5.addCuentaBancaria(new CuentaBancaria(cliente5.getDni(), TipoCuenta.PREMIUM, 120000.0));
        clienteRepository.save(cliente5);

        System.out.println("H2 database with initial clients and accounts has been initialized.");
    }
}
