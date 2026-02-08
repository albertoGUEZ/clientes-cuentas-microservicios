package com.alberto.clientes_cuentas_microservicios.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class ClienteEntity {
    @Id
    private String dni;

    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaBancariaEntity> cuentas = new ArrayList<>();
}
