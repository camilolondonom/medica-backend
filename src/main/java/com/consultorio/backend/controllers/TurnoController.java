package com.consultorio.backend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class TurnoController {

    @MessageMapping("/llamar-turno")
    @SendTo("/topic/turnos")
    public Map<String, Object> llamarTurno(Map<String, Object> turnoData) {
        return turnoData;
    }
}