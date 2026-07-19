package com.consultorio.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.backend.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Cambiado de findByUsername a findById (que mapea con documento)
    // O puedes usar findByDocumento si prefieres mantener la semántica:
    Optional<Usuario> findByDocumento(Long documento);
}
