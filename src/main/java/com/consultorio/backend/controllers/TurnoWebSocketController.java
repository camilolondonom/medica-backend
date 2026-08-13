package com.consultorio.backend.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TurnoWebSocketController {

    @MessageMapping("/nuevo-turno")
    @SendTo("/topic/turnos")
    public Object registrarNuevoTurno(Object turno) {
        // Retorna el turno a todos los clientes suscritos (Recepción, TV, Médica)
        return turno;
    }

    @MessageMapping("/actualizar-estado-turno")
    @SendTo("/topic/turnos")
    public Object actualizarEstado(Object turnoEstado) {
        return turnoEstado;
    }

    @MessageMapping("/llamar-turno")
    @SendTo("/topic/llamatv")
    public Object llamarTurnoTV(Object llamada) {
        // Este canal /topic/llamatv será escuchado principalmente por la vista del Televisor
        return llamada;
    }
}