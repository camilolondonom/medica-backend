package com.consultorio.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor  // Constructor vacío obligatorio para JPA
@AllArgsConstructor // Constructor completo
public class Usuario {
    
    @Id
    private Long documento; // Ahora es la clave primaria manual
    
    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;
    
    @Column(nullable = false)
    private String password; // Guardará el hash de los 6 dígitos numéricos
    
    @Column(nullable = false)
    private String rol; // Valores: "MEDICO" o "AUXILIAR_ADMINISTRATIVO"
}