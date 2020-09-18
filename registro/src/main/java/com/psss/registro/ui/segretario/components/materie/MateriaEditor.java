package com.psss.registro.ui.segretario.components.materie;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.ServiceFacade;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MateriaEditor extends Div {

    private final MateriaForm form = new MateriaForm();

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");

    private final Details details = new Details();

    private final ListBox<Docente> listBox = new ListBox<>();

    private Dialog dialog;

    private MateriaGrid grid;

    private ServiceFacade serviceFacade;

    public MateriaEditor(ServiceFacade serviceFacade) {

        setId("editor-layout");

        this.serviceFacade = serviceFacade;

        Label titolo = new Label("Scheda materia");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form, details);

        add(formDiv, createButtonLayout());

        createDetails();
    }

    private void createDetails() {
        details.setSummaryText("Docenti");
        details.setContent(listBox);

        listBox.setReadOnly(true);
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
            Materia materia = grid.getGrid().getSelectedItems().iterator().next();
            form.getBinder().writeBeanIfValid(materia);

            Notification notification = new Notification();
            notification.setDuration(3000);
            if(serviceFacade.updateMateria(materia)) {
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setText("Materia aggiornata con successo!");
                notification.open();
                grid.getGrid().setItems(grid.getMaterie());
            } else {
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setText("Attenzione: non è possibile aggiornare la materia!");
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
            Materia materia = grid.getGrid().getSelectedItems().iterator().next();
            serviceFacade.deleteMateriaById(materia.getId());
            Notification notification = new Notification();
            notification.setDuration(3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setText("Materia rimossa con successo!");
            notification.open();
            grid.getMaterie().remove(materia);
            grid.getGrid().setItems(grid.getMaterie());
            dialog.close();
        });
        Button chiudi = new Button("Chiudi");
        chiudi.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        chiudi.addClickListener(e -> dialog.close());
        buttonLayout.add(conferma, chiudi);

        dialog.add(buttonLayout);
    }

    public MateriaForm getForm() {
        return form;
    }

    public Details getDetails() {
        return details;
    }

    public ListBox<Docente> getListBox() {
        return listBox;
    }

    public void setGrid(MateriaGrid grid) {
        this.grid = grid;
    }
}
