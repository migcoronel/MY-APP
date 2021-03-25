package com.example.application.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;

@CssImport("./shared-styles.css")
public class MainLayout extends AppLayout {
    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Projects Demo | MigCoronel");
        logo.addClassName("logo");
        Image mainIcon = new Image("images/technology.png", "Icon");
        mainIcon.getElement().getStyle().set("margin-left","auto");
        mainIcon.setHeight("60px");
        mainIcon.setWidth("60px");
        mainIcon.getElement().getStyle().set("padding-right","10px");
        mainIcon.getElement().getStyle().set("padding-top","10px");
        mainIcon.getElement().getStyle().set("padding-bottom","5px");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,mainIcon);
        header.addClassName("header");
        header.setSpacing(false);
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);



        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("+ Base de Datos", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink apiLink = new RouterLink("+ REST API",ApiView.class);

        Button themeToggle = new Button("Alternar Aspecto",new Icon(VaadinIcon.LIGHTBULB), click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            if (themeList.contains(Lumo.DARK)) themeList.remove(Lumo.DARK);
            else themeList.add(Lumo.DARK);
        });

        themeToggle.getElement().getStyle().set("margin-top","auto");
        themeToggle.addThemeVariants(ButtonVariant.LUMO_ICON);
        VerticalLayout drawerLayout = new VerticalLayout(
                listLink,
                apiLink,
                themeToggle);

        drawerLayout.setHeight("100%");
        themeToggle.setWidthFull();
        addToDrawer(drawerLayout);

    }
}
