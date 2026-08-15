package com.consultorio.backend.dto;

import lombok.Data;

@Data
public class NuevaAtencionRequest {
    private Long documento;
    private String nombreCompleto;
    private String fechaNacimiento; // formato yyyy-MM-dd, opcional
    private String genero;          // "M" o "F", opcional
    private String tipoServicio;    // debe coincidir con el enum TipoServicio
}
