package com.consultorio.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultorio.backend.entities.DiagnosticoAtencion;

public interface DiagnosticoAtencionRepository extends JpaRepository<DiagnosticoAtencion, Integer> {

    // Trae los diagnósticos de una atención específica, en orden (1, 2, 3)
    List<DiagnosticoAtencion> findByAtencion_IdAtencionOrderByOrdenAsc(Integer idAtencion);
}
