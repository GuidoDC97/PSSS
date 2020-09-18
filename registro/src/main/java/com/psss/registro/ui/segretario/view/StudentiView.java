package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractFactory;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;
import com.psss.registro.ui.segretario.components.studenti.StudenteFactory;

import com.vaadin.flow.component.Component;
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

        AbstractFactory abstractFactory = new StudenteFactory(this.serviceFacade);
        AbstractGrid grid = abstractFactory.createGrid();
        AbstractEditor editor = abstractFactory.createEditor();
        editor.setVisible(false);

        grid.setEditor(editor);
        editor.setGrid(grid);

        splitLayout.addToPrimary((Component) grid);
        splitLayout.addToSecondary((Component) editor);

        add(splitLayout);
    }
}
