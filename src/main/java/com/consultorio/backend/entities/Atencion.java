package com.consultorio.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "atenciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion")
    private Integer idAtencion;

    // RELACIÓN: Muchas atenciones pertenecen a un único paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documento", nullable = false)
    private Paciente paciente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio", nullable = false)
    private TipoServicio tipoServicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_turno", nullable = false)
    private EstadoTurno estadoTurno = EstadoTurno.ESPERA;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha = LocalDate.now();

    @Column(name = "hora_llegada", nullable = false)
    private LocalTime horaLlegada;

    // --- ENUMS PARA CONTEXTO ESTRICTO DEL NEGOCIO ---
    
    public enum TipoServicio {
        CONSULTA_GENERAL, CERT_MAYOR, CERT_MENOR, HUELLA, CTO_DLLO, REVISION_CONSULTA
    }

    public enum EstadoTurno {
        ESPERA, AUSENTE, LLAMADO, CONSULTA, ATENDIDO
    }
}
