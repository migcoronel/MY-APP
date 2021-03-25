package com.example.application.backend.empleado;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    private Integer dni;
    private String nombre;
    private String apellido;
    private LocalDate fdn;
    private String sexo;
    private Integer salario;
    private Integer dni_supervisor;

    public Empleado() {
    }

    public Empleado(Integer dni, String nombre, String apellido, LocalDate fdn, String sexo, Integer salario, Integer dni_supervisor) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fdn = fdn;
        this.sexo = sexo;
        this.salario = salario;
        this.dni_supervisor = dni_supervisor;
    }


    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
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

    public LocalDate getFdn() {
        return fdn;
    }

    public void setFdn(LocalDate fdn) {
        this.fdn = fdn;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public Integer getDni_supervisor() {
        return dni_supervisor;
    }

    public void setDni_supervisor(Integer dni_supervisor) {
        this.dni_supervisor = dni_supervisor;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fdn='" + fdn + '\'' +
                ", sexo='" + sexo + '\'' +
                ", salario=" + salario +
                ", dni_supervisor=" + dni_supervisor +
                '}';
    }
}
