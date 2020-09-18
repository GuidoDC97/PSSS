package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.services.ServiceFacade;
import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractFactory;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;

public class ClasseFactory implements AbstractFactory {

    private ServiceFacade serviceFacade;

    public ClasseFactory(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @Override
    public AbstractGrid createGrid() {
        return new ClasseGrid(serviceFacade);
    }

    @Override
    public AbstractEditor createEditor() {
        return new ClasseEditor(serviceFacade);
    }
}
