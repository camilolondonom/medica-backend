package com.consultorio.backend.entities;

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
@Table(name = "diagnosticos_atencion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_diagnostico")
    private Integer idDiagnostico;

    // RELACIÓN: Varios diagnósticos pertenecen a una única atención (máx. 3)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_atencion", nullable = false)
    private Atencion atencion;

    @Column(name = "codigo_dx", nullable = false, length = 10)
    private String codigoDx;

    @Column(name = "orden", nullable = false)
    private Integer orden;
}
