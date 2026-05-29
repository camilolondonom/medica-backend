package com.consultorio.backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.backend.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, java.lang.Integer> {
    
    // Query Method: Spring Data genera el SQL automáticamente: SELECT * FROM usuarios WHERE username = ?
    Optional<Usuario> findByUsername(String username);
}
