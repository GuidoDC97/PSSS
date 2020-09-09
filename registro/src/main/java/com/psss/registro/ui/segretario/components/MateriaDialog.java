package com.psss.registro.ui.segretario.components;

import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.MateriaService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MateriaDialog extends Dialog {

    private final MateriaForm form = new MateriaForm();

    private final Button conferma = new Button("Conferma");

    private MateriaGrid grid;

    private MateriaService materiaService;

    public MateriaDialog(MateriaService materiaService) {
        setId("editor-layout");

        this.materiaService = materiaService;

        Label titolo = new Label("Nuova materia");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        add(formDiv, createButtonLayout(formDiv));

        addOpenedChangeListener(e -> {
            if(!e.isOpened()) {
                form.getBinder().readBean(null);
            }
        });
    }

    private HorizontalLayout createButtonLayout(Div formDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(false);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        conferma.addClickShortcut(Key.ENTER).listenOn(formDiv);
        conferma.addClickListener(e -> {
            Materia materia = new Materia();
            form.getBinder().writeBeanIfValid(materia);
            materiaService.saveMateria(materia);
            Notification.show("Materia aggiornata con successo!");
            System.out.println("Materia aggiunta: " + materia.toString());
            grid.getMaterie().add(materia);
            grid.getGrid().setItems(grid.getMaterie());
            close();
        });

        buttonLayout.add(conferma);

        return buttonLayout;
    }

    public void setGrid(MateriaGrid grid) {
        this.grid = grid;
    }
}
