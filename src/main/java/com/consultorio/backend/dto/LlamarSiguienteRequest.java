package com.consultorio.backend.dto;

import java.util.List;
import lombok.Data;

@Data
public class LlamarSiguienteRequest {
    private List<String> diagnosticos; // 0 a 3 códigos DX del paciente que se cierra
}