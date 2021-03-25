package com.example.application.ui;

import com.example.application.backend.empleado.Empleado;
import com.example.application.backend.empleado.EmpleadoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "main" , layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Projects Demo | MigCoronel")
public class ListView extends VerticalLayout {

    Grid<Empleado> grid = new Grid<>(Empleado.class);
    TextField textField = new TextField();

    private final EmpleadoService empleadoService;
    private final EmpleadoForm empleadoForm;

    @Autowired
    public ListView(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
        addClassName("main-view");
        setSizeFull();
        configureGrid();

        empleadoForm = new EmpleadoForm(empleadoService);
        empleadoForm.addListener(EmpleadoForm.SaveEvent.class , this::saveEmpleado);
        empleadoForm.addListener(EmpleadoForm.DeleteEvent.class , this::deleteEmpleado);
        empleadoForm.addListener(EmpleadoForm.CloseEvent.class , e -> closeEditor());

        Div content = new Div(grid, empleadoForm);

        content.addClassName("main-content");
        content.setSizeFull();

        add(getToolBar(),content);
        updateList();
        closeEditor();


        //DUMMY LOGIN PAGE
        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("Iniciar Sesión");
        loginOverlay.setDescription("Escriba un usuario y una contraseña para entrar a la base de datos.");
        loginOverlay.addLoginListener(e -> loginOverlay.close());
        loginOverlay.setOpened(true);
        LoginI18n login = LoginI18n.createDefault();
        login.setAdditionalInformation("Introduzca cualquier usuario y contraseña para cerrar.");
        loginOverlay.setI18n(login);
        add(loginOverlay);
    }

    private void deleteEmpleado(EmpleadoForm.DeleteEvent evt) {
        empleadoService.deleteEmpleado(evt.getEmpleado().getDni());
        updateList();
        closeEditor();
    }

    private void saveEmpleado(EmpleadoForm.SaveEvent evt) {
        empleadoService.save(evt.getEmpleado());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        empleadoForm.setEmpleado(null);
        empleadoForm.setVisible(false);
        empleadoForm.no_supervisor.clear();
        removeClassName("editing");
    }

    private HorizontalLayout getToolBar() {
        textField.setPlaceholder("Filtrar por nombre...");
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.addValueChangeListener(e -> updateList());

        Button btnAgregarEmpleado = new Button("Agregar Empleado");
        btnAgregarEmpleado.addClickListener(click -> addEmpleado());

        HorizontalLayout toolBar = new HorizontalLayout(textField, btnAgregarEmpleado);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    private void addEmpleado() {
        grid.asSingleSelect().clear();
        editEmpleado(new Empleado());
    }

    private void updateList() {
        grid.setItems(empleadoService.getEmpleados(textField.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("empleado-grid");
        grid.setSizeFull();
        grid.setColumns("dni",
                "nombre" ,
                "apellido" ,
                "sexo" ,
                "fdn" ,
                "salario" ,
                "dni_supervisor");
        grid.addColumn(empleado -> {
            String s;
            Integer super_dni = empleado.getDni_supervisor();
            if (super_dni == null) s = "Sin Supervisor";
            else if(empleadoService.findbyDni(super_dni).isPresent()) {
                s = empleadoService.findbyDni(super_dni).get().getNombre();
                s += (" " + empleadoService.findbyDni(super_dni).get().getApellido());
            } else {
                s = "Sin Supervisor";
            }
            return s;
        }).setHeader("Supervisor");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> {
            empleadoForm.setEmpleado(null);
            empleadoForm.no_supervisor.clear();
            editEmpleado(evt.getValue());
            
        });
    }

    private void editEmpleado(Empleado empleado) {
        if (empleado == null){
            closeEditor();
        } else {
            empleadoForm.no_supervisor.clear();
            empleadoForm.updateDni_supervisor();
            empleadoForm.setEmpleado(empleado);
            empleadoForm.setVisible(true);
            addClassName("editing");
            if(empleado.getDni_supervisor()!=null){ empleadoForm.no_supervisor.setValue(false);}
            else {empleadoForm.no_supervisor.setValue(true);}
        }
    }

}
