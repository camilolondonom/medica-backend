package com.consultorio.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultorio.backend.entities.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    // Aquí irán tus Query Methods personalizados cuando los necesitemos
}

