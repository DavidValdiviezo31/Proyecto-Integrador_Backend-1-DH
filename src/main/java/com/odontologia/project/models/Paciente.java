package com.odontologia.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private Long dni;
  private String nombre;
  private String apellido;
  @OneToOne(cascade = CascadeType.ALL)
  private Domicilio domicilio;
  private LocalDate fechaAlta;
  @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Turno> turnos;
}
