package com.consultorio.backend.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "documentos_emitidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoEmitido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento_emitido")
    private Integer idDocumentoEmitido;

    // CORRECCIÓN: Apuntar al ID del Paciente en la base de datos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false) 
    private Paciente paciente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision = LocalDate.now();

    @Column(name = "contenido_dinamico", nullable = false, columnDefinition = "TEXT")
    private String contenidoDinamico;

    public enum TipoDocumento {
        MEDICO_GENERAL,
        HUELLA,
        FORMULA_EXTRA,
        ASISTENCIA
    }
}