package com.consultorio.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.consultorio.backend.entities.Usuario; // Importación nueva
import com.consultorio.backend.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Genera el constructor para campos final
public class UsuarioService {

    // Cambiamos @Autowired por final
    private final UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El nombre de usuario ya está en uso.");
        }
        return usuarioRepository.save(usuario);
    }
}
