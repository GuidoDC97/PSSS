package com.psss.registro.ui.segretario.components.studenti;


import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.ServiceFacade;

import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


class StudenteEditor extends Div implements AbstractEditor {
    private StudenteForm form;

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");

    private final ListBox<Docente> listBox = new ListBox<>();

    private Dialog dialog;

    private StudenteGrid grid;

    private ServiceFacade serviceFacade;

    public StudenteEditor(ServiceFacade serviceFacade) {

        setId("editor-layout");

        this.serviceFacade = serviceFacade;

        form = new StudenteForm(this.serviceFacade);

        Label titolo = new Label("Scheda studente");
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
            Studente studente = grid.getGrid().getSelectedItems().iterator().next();
            form.getBinder().writeBeanIfValid(studente);

            Notification notification = new Notification();
            notification.setDuration(3000);
           if(serviceFacade.updateStudente(studente)){
               notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
               notification.setText("Studente aggiornato con successo!");
               notification.open();
               grid.getGrid().setItems(grid.getStudenti());
        }else {
               notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
               notification.setText("Attenzione: non è possibile aggiornare lo studente!");
               notification.open();
           }});

        form.getBinder().addStatusChangeListener(e -> aggiorna.setEnabled(form.getBinder().isValid()));

        buttonLayout.add(aggiorna, elimina);

        return buttonLayout;
    }

    private void createDialog(){
        dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Div dialogDiv = new Div();
        dialogDiv.setId("editor");
        dialog.add(dialogDiv);

        Label text = new Label("Sei sicuro di voler eliminare uno studente?");
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
            Studente studente = grid.getGrid().getSelectedItems().iterator().next();
            serviceFacade.deleteStudenteById(studente.getId());
            Notification notification = new Notification();
            notification.setDuration(3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setText("Studente rimosso con successo!");
            notification.open();
            grid.getStudenti().remove(studente);
            grid.getGrid().setItems(grid.getStudenti());
            dialog.close();
        });
        Button chiudi = new Button("Chiudi");
        chiudi.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        chiudi.addClickListener(e -> dialog.close());
        buttonLayout.add(conferma, chiudi);

        dialog.add(buttonLayout);
    }

    public StudenteForm getForm() {
        return form;
    }

    public void setGrid(AbstractGrid grid) {
        this.grid = (StudenteGrid) grid;
    }
}
