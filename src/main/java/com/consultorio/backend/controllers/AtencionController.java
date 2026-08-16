package com.consultorio.backend.controllers;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.backend.dto.LlamarSiguienteRequest;
import com.consultorio.backend.dto.NuevaAtencionRequest;
import com.consultorio.backend.services.AtencionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/atenciones")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AtencionController {

    private final AtencionService atencionService;

    @PostMapping
    public ResponseEntity<?> registrarIngreso(@RequestBody NuevaAtencionRequest req) {
        try {
            return ResponseEntity.ok(atencionService.registrarIngreso(req));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarDia(
            @RequestParam(required = false) String fecha) {
        LocalDate f = (fecha != null) ? LocalDate.parse(fecha) : LocalDate.now();
        return ResponseEntity.ok(atencionService.listarDia(f));
    }

    @GetMapping("/atendidos")
    public ResponseEntity<?> listarAtendidos(
            @RequestParam(required = false) String fecha) {
        LocalDate f = (fecha != null) ? LocalDate.parse(fecha) : LocalDate.now();
        return ResponseEntity.ok(atencionService.listarAtendidos(f));
    }

    @PatchMapping("/{id}/ausente")
    public ResponseEntity<?> marcarAusente(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(atencionService.marcarAusente(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/espera")
    public ResponseEntity<?> marcarEnEspera(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(atencionService.marcarEnEspera(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/confirmar-ingreso")
    public ResponseEntity<?> confirmarIngreso(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(atencionService.confirmarIngreso(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/llamar-siguiente")
    public ResponseEntity<?> llamarSiguiente(@RequestBody(required = false) LlamarSiguienteRequest req) {
        try {
            LlamarSiguienteRequest body = (req != null) ? req : new LlamarSiguienteRequest();
            return ResponseEntity.ok(atencionService.llamarSiguiente(body));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
