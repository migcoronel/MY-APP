package com.example.application.backend.empleado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmpleadoService {
    private static final Logger LOGGER = Logger.getLogger(EmpleadoService.class.getName());

    private final EmpleadoRepository empleadoRepository;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> getEmpleados(){
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> findbyDni(Integer dni){
        return empleadoRepository.findById(dni);
    }

    public void addNewEmpleado(Empleado empleado) {
        if (empleadoRepository.existsById(empleado.getDni())) throw new IllegalStateException("DNI Registrado previamente");
        empleadoRepository.save(empleado);
    }

    public void deleteEmpleado(Integer empleadoDni) {
        if (!(empleadoRepository.existsById(empleadoDni))) throw new IllegalStateException("El Empleado con el DNI: "+empleadoDni+", no existe!");
        empleadoRepository.deleteById(empleadoDni);
    }


    public void save(Empleado empleado) {
        if (empleado == null) {
            LOGGER.log(Level.SEVERE,
                    "Empleado is null. Are you sure you have connected your form to the application?");
            return;
        }
        empleadoRepository.save(empleado);
    }

    public List<Empleado> getEmpleados(String textFieldValue) {
        if (textFieldValue == null || textFieldValue.isEmpty()){
            return  empleadoRepository.findAll();

        } else {
            return empleadoRepository.search(textFieldValue);
        }
    }

}
