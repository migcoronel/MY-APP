package com.example.application.ui;

import com.hilerio.ace.AceEditor;
import com.hilerio.ace.AceMode;
import com.hilerio.ace.AceTheme;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("REST API | MigCoronel")
@Route(value = "rest-api",layout = MainLayout.class)
public class ApiView extends VerticalLayout {
    private TextArea codingArea = new TextArea();

    public ApiView(){
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);



        var title = new H1("Try following HTTP methods on Postman or similar:");
        title.getElement().getStyle().set("font-size","25px");

        AceEditor getEditor = new AceEditor();
        configureAceEditor(getEditor);
        getEditor.setValue(
                "Body:\n"+
                "\t{\n" +
                "        \"dni\": 123459,\n" +
                "        \"nombre\": \"Tony\",\n" +
                "        \"apellido\": \"Batista\",\n" +
                "        \"fdn\": \"1961-11-12\",\n" +
                "        \"sexo\": \"M\",\n" +
                "        \"salario\": 999879,\n" +
                "        \"dni_supervisor\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"dni\": 982325,\n" +
                "        \"nombre\": \"Scott\",\n" +
                "        \"apellido\": \"Sheridan\",\n" +
                "        \"fdn\": \"1960-03-10\",\n" +
                "        \"sexo\": \"M\",\n" +
                "        \"salario\": 123654,\n" +
                "        \"dni_supervisor\": null\n" +
                "\t}");
        var getLayout = new VerticalLayout(
                new Label("This method would bring you all employees on 'empleado' table.")
                ,getEditor
        );
        Details getAccordion = new Details("GET : http://api.migcoronel.com/api/v1/empleado",getLayout);
        getAccordion.addThemeVariants(DetailsVariant.FILLED);
        getAccordion.setOpened(true);

//----------------------------------------------------------------------------------------------------------------------
        AceEditor postEditor = new AceEditor();
        configureAceEditor(postEditor);
        postEditor.setValue("header: (content-Type: application/json)\n" +
                "body:\n" +
                "\t{\n" +
                "\t\t\"dni\": 95987987,\n" +
                "\t\t\"nombre\": \"Veronica\",\n" +
                "\t\t\"apellido\": \"Pacheco\",\n" +
                "\t\t\"fdn\": \"2000-01-01\",\n" +
                "\t\t\"sexo\": \"F\",\n" +
                "\t\t\"salario\": 999998,\n" +
                "\t\t\"dni_supervisor\": 95000123\n" +
                "\t}");
        var postLayaout = new VerticalLayout(
                new Label("This HTTP method would add a new employee. ie:"),
                postEditor
        );
        Details postAccordion = new Details("POST : http://api.migcoronel.com/api/v1/empleado",postLayaout);
        postAccordion.addThemeVariants(DetailsVariant.FILLED);
        postAccordion.setOpened(true);

//----------------------------------------------------------------------------------------------------------------------
        AceEditor deleteEditor = new AceEditor();
        configureAceEditor(deleteEditor);
        deleteEditor.setValue("{dni} : Integer");
        var deleteLayout = new VerticalLayout(
                new Label("This HTTP method would delete an employee."),
                deleteEditor
        );
        Details deleteAccordion = new Details("DELETE : http://api.migcoronel.com/api/v1/empleado/{{ dni }}",deleteLayout);
        deleteAccordion.addThemeVariants(DetailsVariant.FILLED);
        deleteAccordion.setOpened(true);

//----------------------------------------------------------------------------------------------------------------------
        AceEditor putEditor = new AceEditor();
        configureAceEditor(putEditor);
        putEditor.setValue(
                "Fields' Formats:\n\n"+
                "\tdni : Integer\n" +
                "\tnombre : String\n" +
                "\tapellido : String\n" +
                "\tfdn : String \"YYYY-MM-DD\"\n" +
                "\tsexo : String \"F\" or \"M\"\n" +
                "\tsalario : Integer\n" +
                "\tdni_supervisor : Integer\n\n");
        var putLayout = new VerticalLayout(
                new Label("This HTTP method would modify an employee."),
                putEditor,
                new Label("You could modify one or all the fields.")
        );
        Details putAccordion = new Details("PUT http://api.migcoronel.com/api/v1/empleado/{dni}?{key=value}&(key=value)...",putLayout);
        putAccordion.addThemeVariants(DetailsVariant.FILLED);
        putAccordion.setOpened(true);

//----------------------------------------------------------------------------------------------------------------------
        var methodsLayout = new VerticalLayout();
        methodsLayout.add(getAccordion,postAccordion,deleteAccordion,putAccordion);
        methodsLayout.setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        methodsLayout.setMaxWidth("900px");
        methodsLayout.setMinWidth("350px");

        addClassName("api-view");
        add(title,methodsLayout);
    }

    //Configure code editor
    private void configureAceEditor(AceEditor aceEditor) {
        aceEditor.setTheme(AceTheme.dracula);
        aceEditor.setMode(AceMode.json);
        aceEditor.setFontSize(15);
        aceEditor.setSizeFull();
        aceEditor.setMinlines(4);
        aceEditor.setMaxlines(20);
        aceEditor.setHighlightActiveLine(false);
        aceEditor.setPlaceholder("Your code here");
        aceEditor.setReadOnly(true);
        aceEditor.setCursorPosition(-1);
    }
}
