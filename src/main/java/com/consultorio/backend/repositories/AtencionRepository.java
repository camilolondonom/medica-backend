package com.consultorio.backend.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultorio.backend.entities.Atencion;
import com.consultorio.backend.entities.Atencion.EstadoTurno;

public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

    // Toda la fila del día, en orden de llegada (para el módulo de recepción y la TV)
    List<Atencion> findByFechaOrderByHoraLlegadaAsc(LocalDate fecha);

    // Lista filtrada por estado y fecha (para "atendidos del día")
    List<Atencion> findByFechaAndEstadoTurnoOrderByHoraLlegadaAsc(LocalDate fecha, EstadoTurno estadoTurno);

    // El siguiente paciente en espera, respetando el orden de llegada
    // (salta automáticamente a los que están en AUSENTE, porque no cumplen el filtro)
    Optional<Atencion> findFirstByFechaAndEstadoTurnoOrderByHoraLlegadaAsc(LocalDate fecha, EstadoTurno estadoTurno);
}
