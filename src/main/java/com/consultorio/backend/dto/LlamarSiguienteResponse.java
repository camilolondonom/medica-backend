package com.consultorio.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LlamarSiguienteResponse {
    private AtencionResponse cerrado;     // el que pasó a ATENDIDO (puede ser null)
    private AtencionResponse enConsulta;  // el que pasó a CONSULTA (puede ser null si no hay nadie esperando)
}