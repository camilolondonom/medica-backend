package com.consultorio.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.backend.entities.CrecimientoDesarrollo;

@Repository
public interface CrecimientoDesarrolloRepository extends JpaRepository<CrecimientoDesarrollo, java.lang.Integer> {
}

