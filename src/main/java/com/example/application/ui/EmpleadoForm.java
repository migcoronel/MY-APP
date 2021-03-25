package com.example.application.ui;

import com.example.application.backend.empleado.Empleado;
import com.example.application.backend.empleado.EmpleadoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

import java.util.Objects;
import java.util.TreeSet;

public class EmpleadoForm extends FormLayout {
    private final EmpleadoService empleadoService;


    TextField dni = new TextField("DNI");
    TextField nombre = new TextField("Nombre");
    TextField apellido = new TextField("Apellido");
    RadioButtonGroup<String> sexo = new RadioButtonGroup<>();
    DatePicker fdn = new DatePicker();
    TextField salario = new TextField("Salario");
    ComboBox<Integer> dni_supervisor = new ComboBox<>("DNI Supervisor");
    Checkbox no_supervisor = new Checkbox("No tiene supervisor?");

    Button guardar = new Button("Guardar");
    Button borrar = new Button("Borrar");
    Button cerrar = new Button("Cerrar");

    Binder<Empleado> binder = new Binder<>(Empleado.class);

    public EmpleadoForm(EmpleadoService empleadoService){
        this.empleadoService = empleadoService;
        addClassName("empleado-form");

        dni.setRequiredIndicatorVisible(true);
        dni.setMinLength(6);
        dni.setPlaceholder("Ingrese un DNI");

        sexo.setLabel("Sexo");
        sexo.setItems("F","M");

        fdn.setLabel("Fecha de Nacimiento");
        fdn.setClearButtonVisible(true);

        //salario.setRequired(true);
        salario.setRequiredIndicatorVisible(true);
        salario.setPlaceholder("Ingrese un Salario");


        updateDni_supervisor();
        no_supervisor.setValue(false);
        no_supervisor.addValueChangeListener(evt -> {
            if (no_supervisor.getValue()) hideSupervisor();
            else {dni_supervisor.setVisible(true);}
        });


        //Linkea el binder con este form
        //binder.bindInstanceFields(this);
        binder.forField(dni)
                .withNullRepresentation("")
                .withValidator(Objects::nonNull, "Ingrese un DNI valido, con mas de 6 digitos")
                .withValidator(dni -> dni.length() >= 6 ,  "Ingrese un DNI valido, con mas de 6 digitos")
                .withConverter(new StringToIntegerConverter("Ingrese solo numeros"))
                .bind(Empleado::getDni , Empleado::setDni);
        binder.forField(nombre)
                .bind(Empleado::getNombre, Empleado::setNombre);
        binder.forField(apellido)
                .bind(Empleado::getApellido, Empleado::setApellido);
        binder.forField(sexo)
                .bind(Empleado::getSexo, Empleado::setSexo);
        binder.forField(fdn)
                .bind(Empleado::getFdn , Empleado::setFdn);
        binder.forField(salario)
                .withNullRepresentation("")
                .withValidator(Objects::nonNull , "Ingrese un Salario")
                .withConverter(new StringToIntegerConverter("Ingrese solo numeros"))
                .bind(Empleado::getSalario , Empleado::setSalario);
        binder.forField(dni_supervisor)
                .bind(Empleado::getDni_supervisor , Empleado:: setDni_supervisor);
        



        //agrega todos los componentes al Layout
        add(
                dni,
                nombre,
                apellido,
                sexo,
                fdn,
                salario,
                no_supervisor,
                dni_supervisor,
                createButtonsLayout()
        );

    }

    public void updateDni_supervisor() {
        TreeSet<Integer> dni_supers = new TreeSet<>();
        this.empleadoService.getEmpleados().forEach(
                empleado -> dni_supers.add(empleado.getDni()));
        dni_supervisor.setItems(dni_supers);
        dni_supervisor.setRequiredIndicatorVisible(true);
        dni_supervisor.setErrorMessage("Ingrese un DNI valido");
    }

    private void hideSupervisor() {
        dni_supervisor.setVisible(false);
        dni_supervisor.setValue(null);
    }

    public void setEmpleado (Empleado empleado){
        binder.setBean(empleado);
    }

    private Component createButtonsLayout() {
        //Cambia el look and feel de cada boton
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        borrar.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cerrar.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);


        //Agrega shortcuts a los botones para que puedan ser "accionados"
        //desde el teclado
        guardar.addClickShortcut(Key.ENTER);
        cerrar.addClickShortcut(Key.ESCAPE);

        //Agrega los eventListeners
        guardar.addClickListener(click -> {
            validateAndSave();
            Notification.show("Empleado Agregado / Modificado!",3000, Notification.Position.BOTTOM_CENTER);
        });
        borrar.addClickListener(click -> {
            fireEvent( new DeleteEvent(this,binder.getBean()));
            Notification.show("Empleado Borrado!",3000, Notification.Position.BOTTOM_CENTER);
        });
        cerrar.addClickListener(click -> {
            fireEvent(new CloseEvent(this));
            Notification.show("Empleado Modificado!",3000, Notification.Position.BOTTOM_CENTER);
        });

        binder.addStatusChangeListener(evt -> guardar.setEnabled(binder.isValid()));

        return new HorizontalLayout(guardar,borrar,cerrar);
    }

    private void validateAndSave() {
        if (binder.isValid()) fireEvent(new SaveEvent(this,binder.getBean()));
    }

    // Events
    //-------------------------------------------------------------------
    public static abstract class EmpleadoFormEvent extends ComponentEvent<EmpleadoForm> {
        private Empleado empleado;

        protected EmpleadoFormEvent(EmpleadoForm source, Empleado empleado) {
            super(source, false);
            this.empleado = empleado;
        }

        public Empleado getEmpleado() {
            return empleado;
        }
    }

    public static class SaveEvent extends EmpleadoFormEvent {
        SaveEvent(EmpleadoForm source, Empleado empleado) {
            super(source, empleado);
        }
    }

    public static class DeleteEvent extends EmpleadoFormEvent {
        DeleteEvent(EmpleadoForm source, Empleado empleado) {
            super(source, empleado);
        }

    }

    public static class CloseEvent extends EmpleadoFormEvent {
        CloseEvent(EmpleadoForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
