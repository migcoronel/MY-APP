package com.example.application.backend.empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado , Integer> {

    @Query("select c from Empleado c " +
            "where lower(c.nombre) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.apellido) like lower(concat('%', :searchTerm, '%'))")
    List<Empleado> search(@Param("searchTerm") String searchTerm);
}
