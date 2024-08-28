package com.odontologia.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate fecha;
  private LocalTime hora;
  @ManyToOne(fetch = FetchType.EAGER)
  private Paciente paciente;
  @ManyToOne(fetch = FetchType.EAGER)
  private Odontologo odontologo;
}
