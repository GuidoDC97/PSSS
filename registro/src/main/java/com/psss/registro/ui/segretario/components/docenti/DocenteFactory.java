package com.psss.registro.ui.segretario.components.docenti;


import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractFactory;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;

public class DocenteFactory implements AbstractFactory {

    private ServiceFacade serviceFacade;

    public DocenteFactory(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @Override
    public AbstractGrid createGrid() {
        return new DocenteGrid(serviceFacade);
    }

    @Override
    public AbstractEditor createEditor() {
        return new DocenteEditor(serviceFacade);
    }

}
