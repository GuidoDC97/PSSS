package com.psss.registro.ui.segretario.components.materie;

import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.MateriaService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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

        form.getCodice().setAutofocus(true);

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
        conferma.setEnabled(false);
        conferma.addClickListener(e -> {
            Materia materia = new Materia();
            form.getBinder().writeBeanIfValid(materia);

            Notification notification = new Notification();
            notification.setDuration(3000);
            if(materiaService.saveMateria(materia)) {
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setText("Materia inserita con successo!");
                notification.open();
                grid.getMaterie().add(materia);
                grid.getGrid().setItems(grid.getMaterie());
                close();
            } else {
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setText("Attenzione: non è possibile inserire la materia!");
                notification.open();
            }
        });

        form.getBinder().addStatusChangeListener(e -> conferma.setEnabled(form.getBinder().isValid()));

        buttonLayout.add(conferma);

        return buttonLayout;
    }

    public void setGrid(MateriaGrid grid) {
        this.grid = grid;
    }
}
