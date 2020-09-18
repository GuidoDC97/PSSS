package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.components.materie.MateriaEditor;
import com.psss.registro.ui.segretario.components.materie.MateriaGrid;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "segretario/materie", layout = MainView.class)
@PageTitle("Materie")
@CssImport("./styles/views/materie/materie-view.css")
public class MaterieView extends Div {

    private ServiceFacade serviceFacade;

    public MaterieView(ServiceFacade serviceFacade) {

        this.serviceFacade = serviceFacade;

        setId("materie-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        MateriaGrid materiaGrid = new MateriaGrid(serviceFacade);
        MateriaEditor materiaEditor = new MateriaEditor(serviceFacade);
        materiaEditor.setVisible(false);

        materiaGrid.setEditor(materiaEditor);
        materiaEditor.setGrid(materiaGrid);

        splitLayout.addToPrimary(materiaGrid);
        splitLayout.addToSecondary(materiaEditor);
        add(splitLayout);
    }
}

