package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.*;
import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractFactory;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;
import com.psss.registro.ui.segretario.components.classi.ClasseFactory;

import com.vaadin.flow.component.Component;
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

        AbstractFactory abstractFactory = new ClasseFactory(this.serviceFacade);
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