package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.*;
import com.psss.registro.ui.segretario.components.classi.ClasseEditor;
import com.psss.registro.ui.segretario.components.classi.ClasseGrid;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "segretario/classi", layout = MainView.class)
@PageTitle("Classi")
@CssImport("./styles/views/classi/classi-view.css")
public class ClassiView extends Div {

    private ServiceFacade serviceFacade;

    public ClassiView(ServiceFacade serviceFacade) {

        this.serviceFacade = serviceFacade;

        setId("classi-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        ClasseGrid classeGrid = new ClasseGrid(serviceFacade);
        ClasseEditor classeEditor = new ClasseEditor(serviceFacade);
        classeEditor.setVisible(false);

        classeGrid.setEditor(classeEditor);
        classeEditor.setGrid(classeGrid);

        splitLayout.addToPrimary(classeGrid);
        splitLayout.addToSecondary(classeEditor);
        add(splitLayout);
    }

}