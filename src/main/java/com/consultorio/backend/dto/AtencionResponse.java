package com.consultorio.backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtencionResponse {
    private Integer idAtencion;
    private Long documento;
    private String nombreCompleto;
    private String tipoServicio;
    private String estadoTurno;
    private String fecha;
    private String horaLlegada;
    private List<String> diagnosticos;
}