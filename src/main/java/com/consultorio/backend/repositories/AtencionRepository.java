package com.consultorio.backend.repositories;

import com.consultorio.backend.entities.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtencionRepository extends JpaRepository<Atencion, Integer> {
    // Repositorio completamente limpio
}
