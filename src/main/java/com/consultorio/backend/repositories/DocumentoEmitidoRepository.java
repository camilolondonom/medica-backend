package com.consultorio.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.backend.entities.DocumentoEmitido;

@Repository
public interface DocumentoEmitidoRepository extends JpaRepository<DocumentoEmitido, java.lang.Integer> {
}
