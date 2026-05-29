package com.consultorio.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.backend.entities.Atencion;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, java.lang.Integer> {
}
