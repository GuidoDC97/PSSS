package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractFactory;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;
import com.psss.registro.ui.segretario.components.materie.MateriaFactory;

import com.vaadin.flow.component.Component;
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

        AbstractFactory abstractFactory = new MateriaFactory(this.serviceFacade);
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

