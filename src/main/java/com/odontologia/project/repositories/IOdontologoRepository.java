package com.odontologia.project.repositories;

import com.odontologia.project.models.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {
    boolean existsByMatricula(Long matricula);
}
