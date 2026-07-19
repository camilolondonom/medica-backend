package com.consultorio.backend.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.consultorio.backend.entities.Usuario;
import com.consultorio.backend.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        // Buscamos por documento en lugar de username
        Optional<Usuario> usuarioExistente = usuarioRepository.findByDocumento(usuario.getDocumento());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("El número de documento ya se encuentra registrado.");
        }
        return usuarioRepository.save(usuario);
    }
}
