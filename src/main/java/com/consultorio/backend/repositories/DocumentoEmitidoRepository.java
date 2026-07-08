package com.consultorio.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consultorio.backend.entities.DocumentoEmitido;

public interface DocumentoEmitidoRepository extends JpaRepository<DocumentoEmitido, Integer> {
    // 
}
