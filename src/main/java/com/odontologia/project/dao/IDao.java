package com.odontologia.project.dao;

import java.util.List;

public interface IDao<T> {
  // Create
  T guardar(T t);

  // Read
  T buscarPorId(Long id);
  List<T> buscarTodos();

  // Update
  T actualizar(T t);

  // Delete
  T eliminarPorId(Long id);
}
