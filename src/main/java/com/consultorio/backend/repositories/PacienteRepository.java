package com.consultorio.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.backend.entities.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // Aquí mapearemos búsquedas personalizadas más adelante (ej: buscar por nombre)
}
