package com.odontologia.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "odontologos")
public class Odontologo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true)
  private Long matricula;
  private String nombre;
  private String apellido;
  @OneToMany(mappedBy = "odontologo", cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Turno> turnos;
}
