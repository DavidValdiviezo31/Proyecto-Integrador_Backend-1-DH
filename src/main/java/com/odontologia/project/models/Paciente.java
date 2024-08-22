package com.odontologia.project.models;

import java.time.LocalDate;

public class Paciente {
  private Long id;
  private Long dni;
  private String nombre;
  private String apellido;
  private Domicilio domicilio;
  private LocalDate fechaAlta;

  public Paciente() {}

  public Paciente(Long dni, String nombre, String apellido, Domicilio domicilio, LocalDate fechaAlta) {
    this.dni = dni;
    this.nombre = nombre;
    this.apellido = apellido;
    this.domicilio = domicilio;
    this.fechaAlta = fechaAlta;
  }

  public Paciente(Long id, Long dni, String nombre, String apellido, Domicilio domicilio, LocalDate fechaAlta) {
    this.id = id;
    this.dni = dni;
    this.nombre = nombre;
    this.apellido = apellido;
    this.domicilio = domicilio;
    this.fechaAlta = fechaAlta;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDni() {
    return dni;
  }

  public void setDni(Long dni) {
    this.dni = dni;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public Domicilio getDomicilio() {
    return domicilio;
  }

  public void setDomicilio(Domicilio domicilio) {
    this.domicilio = domicilio;
  }

  public LocalDate getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(LocalDate fechaAlta) {
    this.fechaAlta = fechaAlta;
  }
}
