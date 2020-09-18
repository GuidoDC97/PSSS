package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.components.docenti.DocenteEditor;
import com.psss.registro.ui.segretario.components.docenti.DocenteGrid;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "segretario/docenti", layout = MainView.class)
@PageTitle("Docenti")
@CssImport("./styles/views/docenti/docenti-view.css")
public class DocentiView extends Div {

    private ServiceFacade serviceFacade;

    public DocentiView(ServiceFacade serviceFacade) {

        this.serviceFacade = serviceFacade;

        setId("docenti-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        DocenteGrid docenteGrid = new DocenteGrid(this.serviceFacade);
        DocenteEditor docenteEditor = new DocenteEditor(this.serviceFacade);
        docenteEditor.setVisible(false);

        docenteGrid.setEditor(docenteEditor);
        docenteEditor.setGrid(docenteGrid);

        splitLayout.addToPrimary(docenteGrid);
        splitLayout.addToSecondary(docenteEditor);
        add(splitLayout);
    }

}