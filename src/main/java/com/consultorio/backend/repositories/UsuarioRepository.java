package com.consultorio.backend.repositories;

import com.consultorio.backend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Query Method: Busca el usuario por su username
    Optional<Usuario> findByUsername(String username);
}
