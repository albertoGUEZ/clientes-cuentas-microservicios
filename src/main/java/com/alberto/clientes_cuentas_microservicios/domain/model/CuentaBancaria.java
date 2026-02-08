package com.alberto.clientes_cuentas_microservicios.domain.model;


import java.util.Objects;

public class CuentaBancaria {

    private Long id;
    private final Dni dniCliente;
    private final TipoCuenta tipoCuenta;
    private Double total;

    public CuentaBancaria(Dni dniCliente, TipoCuenta tipoCuenta, Double total) {
        if (dniCliente == null) throw new IllegalArgumentException("dniCliente no puede ser null");
        if (tipoCuenta == null) throw new IllegalArgumentException("tipoCuenta no puede ser null");
        if (total == null || total < 0) throw new IllegalArgumentException("El valor total no puede ser negativo");

        this.dniCliente = dniCliente;
        this.tipoCuenta = tipoCuenta;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Dni getDniCliente() {
        return dniCliente;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Double getTotal() {
        return total;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("El id ya ha sido asignado");
        }
        this.id = id;
    }

    public void updateTotal(Double total) {
        if (total == null || total < 0) {
            throw new IllegalArgumentException("El valor total no puede ser negativo");
        }
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CuentaBancaria cuentaBancaria)) return false;
        return Objects.equals(id, cuentaBancaria.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
