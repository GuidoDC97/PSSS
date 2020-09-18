package com.psss.registro.ui.segretario.components.materie;


import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractFactory;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;

public class MateriaFactory implements AbstractFactory {

    private ServiceFacade serviceFacade;

    public MateriaFactory(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @Override
    public AbstractGrid createGrid() {
        return new MateriaGrid(serviceFacade);
    }

    @Override
    public AbstractEditor createEditor() {
        return new MateriaEditor(serviceFacade);
    }

}
