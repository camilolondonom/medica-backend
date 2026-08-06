package com.consultorio.backend.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.backend.entities.Usuario;
import com.consultorio.backend.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor // Resuelve la advertencia de inyección por constructor automáticamente
public class UsuarioController {

    // Al ser final, Lombok genera el constructor requerido y Spring inyecta el
    // servicio
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Respuesta limpia en UsuarioController.java
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorDocumento(usuario.getDocumento());

        if (usuarioOpt.isPresent()) {
            Usuario usuarioDb = usuarioOpt.get();
            if (usuarioDb.getPassword().equals(usuario.getPassword())) {
                // Ocultamos la contraseña antes de retornar la respuesta
                usuarioDb.setPassword(null);
                return ResponseEntity.ok(usuarioDb);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Documento o contraseña incorrectos");
    }
}