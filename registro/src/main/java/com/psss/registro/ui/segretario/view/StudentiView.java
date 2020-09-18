package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.components.studenti.StudenteGrid;
import com.psss.registro.ui.segretario.components.studenti.StudenteEditor;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "segretario/studenti", layout = MainView.class)
@PageTitle("Studenti")
@CssImport("./styles/views/studenti/studenti-view.css")
public class StudentiView extends Div {

    private ServiceFacade serviceFacade;

    public StudentiView(ServiceFacade serviceFacade) {

        this.serviceFacade = serviceFacade;

        setId("studenti-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        StudenteGrid studenteGrid = new StudenteGrid(this.serviceFacade);
        StudenteEditor studenteEditor = new StudenteEditor(this.serviceFacade);
        studenteEditor.setVisible(false);

        studenteGrid.setEditor(studenteEditor);
        studenteEditor.setGrid(studenteGrid);

        splitLayout.addToPrimary(studenteGrid);
        splitLayout.addToSecondary(studenteEditor);
        add(splitLayout);
    }
}
