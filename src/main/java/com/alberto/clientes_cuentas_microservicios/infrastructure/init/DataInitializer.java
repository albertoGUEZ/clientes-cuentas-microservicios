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
    private final CuentaBancariaRepositoryPort cuentaBancariaRepository;

    public DataInitializer(ClienteRepositoryPort clienteRepository,
                           CuentaBancariaRepositoryPort cuentaBancariaRepository) {
        this.clienteRepository = clienteRepository;
        this.cuentaBancariaRepository = cuentaBancariaRepository;
    }

    @Override
    public void run(String... args) {
        Cliente cliente1 = ClienteFactory.createWithAllData(
                new Dni("11111111A"), "Juan", "Pérez", "López", LocalDate.of(1959, 9, 12)
        );
        CuentaBancaria c1 = new CuentaBancaria(cliente1.getDni(), TipoCuenta.PREMIUM, 150000.0);
        CuentaBancaria c2 = new CuentaBancaria(cliente1.getDni(), TipoCuenta.NORMAL, 20000.0);
        cliente1.addCuentaBancaria(c1);
        cliente1.addCuentaBancaria(c2);
        clienteRepository.save(cliente1);
        cuentaBancariaRepository.save(c1);
        cuentaBancariaRepository.save(c2);

        Cliente cliente2 = ClienteFactory.createWithAllData(
                new Dni("22222222B"), "Raúl", "Canales", "Rodríguez", LocalDate.of(1985, 3, 1)
        );
        CuentaBancaria c3 = new CuentaBancaria(cliente2.getDni(), TipoCuenta.NORMAL, 50000.0);
        CuentaBancaria c4 = new CuentaBancaria(cliente2.getDni(), TipoCuenta.JUNIOR, 300.0);
        cliente2.addCuentaBancaria(c3);
        cliente2.addCuentaBancaria(c4);
        clienteRepository.save(cliente2);
        cuentaBancariaRepository.save(c3);
        cuentaBancariaRepository.save(c4);

        Cliente cliente3 = ClienteFactory.createWithAllData(
                new Dni("33333333C"), "Elena", "Ruiz", "Herrera", LocalDate.of(2010, 5, 10)
        );
        CuentaBancaria c5 = new CuentaBancaria(cliente3.getDni(), TipoCuenta.JUNIOR, 300.0);
        cliente3.addCuentaBancaria(c5);
        clienteRepository.save(cliente3);
        cuentaBancariaRepository.save(c5);

        Cliente cliente4 = ClienteFactory.createWithAllData(
                new Dni("44444444D"), "Raquel", "Ruiz", "Herrera", LocalDate.of(2002, 6, 21)
        );
        CuentaBancaria c6 = new CuentaBancaria(cliente4.getDni(), TipoCuenta.NORMAL, 75000.0);
        cliente4.addCuentaBancaria(c6);
        clienteRepository.save(cliente4);
        cuentaBancariaRepository.save(c6);

        Cliente cliente5 = ClienteFactory.createWithAllData(
                new Dni("55555555E"), "María", "Sánchez", "Torres", LocalDate.of(1999, 8, 8)
        );
        CuentaBancaria c7 = new CuentaBancaria(cliente5.getDni(), TipoCuenta.PREMIUM, 120000.0);
        cliente5.addCuentaBancaria(c7);
        clienteRepository.save(cliente5);
        cuentaBancariaRepository.save(c7);

        System.out.println("H2 database with initial clients and accounts has been initialized.");
    }
}
