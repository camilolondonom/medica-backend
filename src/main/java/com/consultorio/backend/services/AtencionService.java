package com.consultorio.backend.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.consultorio.backend.dto.AtencionResponse;
import com.consultorio.backend.dto.LlamarSiguienteRequest;
import com.consultorio.backend.dto.LlamarSiguienteResponse;
import com.consultorio.backend.dto.NuevaAtencionRequest;
import com.consultorio.backend.entities.Atencion;
import com.consultorio.backend.entities.Atencion.EstadoTurno;
import com.consultorio.backend.entities.Atencion.TipoServicio;
import com.consultorio.backend.entities.DiagnosticoAtencion;
import com.consultorio.backend.entities.Paciente;
import com.consultorio.backend.repositories.AtencionRepository;
import com.consultorio.backend.repositories.DiagnosticoAtencionRepository;
import com.consultorio.backend.repositories.PacienteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtencionService {

    private final AtencionRepository atencionRepository;
    private final PacienteRepository pacienteRepository;
    private final DiagnosticoAtencionRepository diagnosticoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // --- REGISTRO DE INGRESO (Admisión en Recepción) ---
    @Transactional
    public AtencionResponse registrarIngreso(NuevaAtencionRequest req) {
        Paciente paciente = pacienteRepository.findById(req.getDocumento())
                .orElseGet(() -> {
                    Paciente nuevo = new Paciente();
                    nuevo.setIdDocumento(req.getDocumento());
                                nuevo.setNombreCompleto(req.getNombreCompleto().trim().toUpperCase());
                    if (req.getFechaNacimiento() != null && !req.getFechaNacimiento().isBlank()) {
                        nuevo.setFechaNacimiento(LocalDate.parse(req.getFechaNacimiento()));
                    }
                    if (req.getGenero() != null && !req.getGenero().isBlank()) {
                        nuevo.setGenero(Paciente.Genero.valueOf(req.getGenero()));
                    }
                    return pacienteRepository.save(nuevo);
                });

        Atencion atencion = new Atencion();
        atencion.setPaciente(paciente);
        atencion.setTipoServicio(TipoServicio.valueOf(req.getTipoServicio()));
        atencion.setEstadoTurno(EstadoTurno.ESPERA);
        atencion.setFecha(LocalDate.now());
        atencion.setHoraLlegada(LocalTime.now());

        Atencion guardada = atencionRepository.save(atencion);
        broadcastListaDelDia();
        return toResponse(guardada, List.of());
    }

    // --- TOGGLE AUSENTE / ESPERA (control de Recepción) ---
    @Transactional
    public AtencionResponse marcarAusente(Integer idAtencion) {
        Atencion atencion = obtenerOArrojar(idAtencion);
        EstadoTurno estadoActual = atencion.getEstadoTurno();
        if (estadoActual != EstadoTurno.ESPERA && estadoActual != EstadoTurno.LLAMADO) {
            throw new IllegalStateException("Solo se puede marcar Ausente desde Espera o Llamado.");
        }
        atencion.setEstadoTurno(EstadoTurno.AUSENTE);
        atencionRepository.save(atencion);
        broadcastListaDelDia();
        return toResponse(atencion, List.of());
    }

    @Transactional
    public AtencionResponse marcarEnEspera(Integer idAtencion) {
        Atencion atencion = obtenerOArrojar(idAtencion);
        if (atencion.getEstadoTurno() != EstadoTurno.AUSENTE) {
            throw new IllegalStateException("Solo se puede regresar a Espera a un paciente marcado como Ausente.");
        }
        atencion.setEstadoTurno(EstadoTurno.ESPERA);
        atencionRepository.save(atencion);
        broadcastListaDelDia();
        return toResponse(atencion, List.of());
    }

    // --- ACCIÓN COMBINADA: cierra al actual (con DX) y llama al siguiente ---
    @Transactional
    public LlamarSiguienteResponse llamarSiguiente(LlamarSiguienteRequest req) {
        LocalDate hoy = LocalDate.now();

        // GUARD: no permitir llamar a otro si ya hay alguien esperando confirmación
        boolean yaHayLlamado = atencionRepository
                .findFirstByFechaAndEstadoTurnoOrderByHoraLlegadaAsc(hoy, EstadoTurno.LLAMADO)
                .isPresent();
        if (yaHayLlamado) {
            throw new IllegalStateException("Ya hay un paciente llamado pendiente de confirmar ingreso.");
        }

        AtencionResponse cerradoDto = null;
        Optional<Atencion> actualEnConsulta = atencionRepository
                .findFirstByFechaAndEstadoTurnoOrderByHoraLlegadaAsc(hoy, EstadoTurno.CONSULTA);

        if (actualEnConsulta.isPresent()) {
            Atencion actual = actualEnConsulta.get();

            if (req.getDiagnosticos() != null) {
                if (req.getDiagnosticos().size() > 3) {
                    throw new IllegalArgumentException("Máximo 3 diagnósticos por atención.");
                }
                int orden = 1;
                for (String codigo : req.getDiagnosticos()) {
                    if (codigo == null || codigo.isBlank())
                        continue;
                    DiagnosticoAtencion dx = new DiagnosticoAtencion();
                    dx.setAtencion(actual);
                        dx.setCodigoDx(codigo.trim().toUpperCase());
                    dx.setOrden(orden++);
                    diagnosticoRepository.save(dx);
                }
            }

            actual.setEstadoTurno(EstadoTurno.ATENDIDO);
            atencionRepository.save(actual);
            cerradoDto = toResponse(actual, listarDxDe(actual.getIdAtencion()));
        }

        AtencionResponse llamadoDto = null;
        Optional<Atencion> siguiente = atencionRepository.findFirstByFechaAndEstadoTurnoOrderByHoraLlegadaAsc(hoy,
                EstadoTurno.ESPERA);

        if (siguiente.isPresent()) {
            Atencion nuevo = siguiente.get();
            nuevo.setEstadoTurno(EstadoTurno.LLAMADO); // <-- antes era CONSULTA
            atencionRepository.save(nuevo);
            llamadoDto = toResponse(nuevo, List.of());
        }

        broadcastListaDelDia();
        return new LlamarSiguienteResponse(cerradoDto, llamadoDto);
    }

    @Transactional
    public AtencionResponse confirmarIngreso(Integer idAtencion) {
        Atencion atencion = obtenerOArrojar(idAtencion);
        if (atencion.getEstadoTurno() != EstadoTurno.LLAMADO) {
            throw new IllegalStateException("Solo se puede confirmar ingreso a un paciente en estado Llamado.");
        }
        atencion.setEstadoTurno(EstadoTurno.CONSULTA);
        atencionRepository.save(atencion);
        broadcastListaDelDia();
        return toResponse(atencion, List.of());
    }

    // --- CONSULTAS ---
    public List<AtencionResponse> listarDia(LocalDate fecha) {
        return atencionRepository.findByFechaOrderByHoraLlegadaAsc(fecha).stream()
                .map(a -> toResponse(a, List.of()))
                .collect(Collectors.toList());
    }

    public List<AtencionResponse> listarAtendidos(LocalDate fecha) {
        return atencionRepository.findByFechaAndEstadoTurnoOrderByHoraLlegadaAsc(fecha, EstadoTurno.ATENDIDO)
                .stream()
                .map(a -> toResponse(a, listarDxDe(a.getIdAtencion())))
                .collect(Collectors.toList());
    }

    // --- HELPERS PRIVADOS ---
    private Atencion obtenerOArrojar(Integer id) {
        return atencionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Atención no encontrada: " + id));
    }

    private List<String> listarDxDe(Integer idAtencion) {
        return diagnosticoRepository.findByAtencion_IdAtencionOrderByOrdenAsc(idAtencion).stream()
                .map(DiagnosticoAtencion::getCodigoDx)
                .collect(Collectors.toList());
    }

    private AtencionResponse toResponse(Atencion a, List<String> diagnosticos) {
        return new AtencionResponse(
                a.getIdAtencion(),
                a.getPaciente().getIdDocumento(),
                a.getPaciente().getNombreCompleto(),
                a.getTipoServicio().name(),
                a.getEstadoTurno().name(),
                a.getFecha().toString(),
                a.getHoraLlegada().toString(),
                diagnosticos);
    }

    private void broadcastListaDelDia() {
        messagingTemplate.convertAndSend("/topic/turnos", listarDia(LocalDate.now()));
    }
}
