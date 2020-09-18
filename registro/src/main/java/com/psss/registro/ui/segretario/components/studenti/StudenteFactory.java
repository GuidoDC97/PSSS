package com.psss.registro.ui.segretario.components.studenti;

import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractFactory;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;


public class StudenteFactory implements AbstractFactory {

    private ServiceFacade serviceFacade;

    public StudenteFactory(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @Override
    public AbstractGrid createGrid() {
        return new StudenteGrid(serviceFacade);
    }

    @Override
    public AbstractEditor createEditor() {
        return new StudenteEditor(serviceFacade);
    }

}
