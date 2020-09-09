package com.psss.registro.ui.segretario.components.docenti;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.services.DocenteService;
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

public class DocenteEditor extends Div {

    private DocenteForm form;

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");

    private Dialog dialog;

    private DocenteGrid grid;

    private DocenteService docenteService;
    private MateriaService materiaService;

    public DocenteEditor(DocenteService docenteService, MateriaService materiaService) {

        setId("editor-layout");

        this.docenteService = docenteService;
        this.materiaService = materiaService;

        form = new DocenteForm(this.materiaService);

        Label titolo = new Label("Scheda docente");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        add(formDiv, createButtonLayout());
    }

    private HorizontalLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(false);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR);
        elimina.addClickListener(event -> {
            createDialog();
            dialog.open();
        });

        aggiorna.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiorna.addClickShortcut(Key.ENTER).listenOn(this);
        aggiorna.addClickListener(event -> {
            Docente docente = grid.getGrid().getSelectedItems().iterator().next();
            form.getBinder().writeBeanIfValid(docente);
            Notification notification = new Notification();
            notification.setDuration(3000);
            if(docenteService.updateDocente(docente)) {
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setText("Materia aggiunta con successo!");
                notification.open();
                grid.getGrid().setItems(grid.getDocenti());
            } else {
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setText("Attenzione: non è possibile aggiungere la materia!");
                notification.open();
            }
        });

        form.getBinder().addStatusChangeListener(e -> aggiorna.setEnabled(form.getBinder().isValid()));

        buttonLayout.add(aggiorna, elimina);

        return buttonLayout;
    }

    private void createDialog() {
        dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Div dialogDiv = new Div();
        dialogDiv.setId("editor");
        dialog.add(dialogDiv);

        Label text = new Label("Sei sicuro di voler eliminare una materia?");
        text.setClassName("text-layout");
        dialogDiv.add(text);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(false);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Button conferma = new Button("Conferma");
        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        conferma.addClickListener(e -> {
            Docente docente = grid.getGrid().getSelectedItems().iterator().next();
            docenteService.deleteById(docente.getId());
            Notification notification = new Notification();
            notification.setDuration(3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setText("Materia rimossa con successo!");
            notification.open();
            grid.getDocenti().remove(docente);
            grid.getGrid().setItems(grid.getDocenti());
            dialog.close();
        });
        Button chiudi = new Button("Chiudi");
        chiudi.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        chiudi.addClickListener(e -> dialog.close());
        buttonLayout.add(conferma, chiudi);

        dialog.add(buttonLayout);
    }

    public DocenteForm getForm() {
        return form;
    }

    public void setGrid(DocenteGrid grid) {
        this.grid = grid;
    }
}
