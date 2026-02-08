package com.alberto.clientes_cuentas_microservicios.domain.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente {
    private final Dni dni;
    private final String nombre;
    private final String apellido1;
    private final String apellido2;
    private final LocalDate fechaNacimiento;
    private final List<CuentaBancaria> cuentas = new ArrayList<>();

    public Cliente(Dni dni, String nombre, String apellido1, String apellido2, LocalDate fechaNacimiento) {
        if (dni == null) throw new IllegalArgumentException("dni no puede ser null");
        if (nombre == null || apellido1 == null || apellido2 == null) {
            throw new IllegalArgumentException("Los nombres y apellidos no pueden ser null");
        }
        if (fechaNacimiento == null) throw new IllegalArgumentException("fechaNacimiento no puede ser null");

        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Dni getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public List<CuentaBancaria> getCuentas() {
        return List.copyOf(cuentas);
    }

    public boolean isAdult() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;
    }

    public void addCuentaBancaria(CuentaBancaria cuentaBancaria) {
        if (!cuentaBancaria.getDniCliente().equals(this.dni)) {
            throw new IllegalArgumentException(
                    "La cuenta no pertenece a este cliente"
            );
        }
        cuentas.add(cuentaBancaria);
    }

    public Double totalBalance() {
        return cuentas.stream()
                .mapToDouble(CuentaBancaria::getTotal)
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return dni.equals(cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }
}
