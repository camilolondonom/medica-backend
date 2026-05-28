package com.consultorio.backend.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crecimiento_desarrollo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrecimientoDesarrollo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer idRegistro;

    // RELACIÓN: Muchos registros de control pertenecen a un único paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documento", nullable = false)
    private Paciente paciente;

    @Column(name = "fecha_toma", nullable = false)
    private LocalDate fechaToma;

    // Usamos BigDecimal para mapear con precisión los campos DECIMAL de SQL
    @Column(name = "peso_kg", nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoKg;

    @Column(name = "talla_cm", nullable = false, precision = 5, scale = 2)
    private BigDecimal tallaCm;

    @Column(name = "imc", nullable = false, precision = 4, scale = 2)
    private BigDecimal imc;

    @Column(name = "perimetro_cefalico", nullable = false, precision = 4, scale = 2)
    private BigDecimal perimetroCefalico;
}