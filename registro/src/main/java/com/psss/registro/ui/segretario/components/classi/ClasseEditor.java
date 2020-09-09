package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.models.*;
import com.psss.registro.backend.services.ClasseService;

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
import org.vaadin.gatanaso.MultiselectComboBox;

public class ClasseEditor extends Div {

    private final ClasseForm form = new ClasseForm();

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");

    private final Details docentiDetails = new Details();

    private final MultiselectComboBox<Insegnamento> insegnamentiCombo = new MultiselectComboBox<>();

    private final Details studentiDetails = new Details();
    private final ListBox<String> studentiList = new ListBox<>();

    private Dialog dialog;

    private ClasseGrid grid;

    private ClasseService classeService;

    public ClasseEditor(ClasseService classeService) {
        setId("editor-layout");

        this.classeService = classeService;

        Label titolo = new Label("Scheda classe");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form, docentiDetails, studentiDetails);

        add(formDiv, createButtonLayout());

        createDocentiDetails();
        createStudentiDetails();
    }



    private void createDocentiDetails() {
        docentiDetails.setSummaryText("Docenti");
        docentiDetails.setContent(insegnamentiCombo);

        insegnamentiCombo.setItemLabelGenerator(Insegnamento::getDocenteMateria);
        insegnamentiCombo.setCompactMode(false);
        insegnamentiCombo.setReadOnly(true);

    }

    private void createStudentiDetails() {
        studentiDetails.setSummaryText("Studenti");
        studentiList.setReadOnly(true);
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
            Classe classe = grid.getGrid().getSelectedItems().iterator().next();
            form.getBinder().writeBeanIfValid(classe);
            Notification notification = new Notification();
            notification.setDuration(3000);

            if(classeService.updateClasse(classe)) {

                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setText("Classe aggiunta con successo!");
                notification.open();
                grid.getGrid().setItems(grid.getClassi());
            } else {
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setText("Attenzione: non è possibile aggiungere la classe!");
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

        Label text = new Label("Sei sicuro di voler eliminare una classe?");
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
            Classe classe = grid.getGrid().getSelectedItems().iterator().next();
            classeService.deleteById(classe.getId());
            Notification notification = new Notification();
            notification.setDuration(3000);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setText("Materia rimossa con successo!");
            notification.open();
            grid.getClassi().remove(classe);
            grid.getGrid().setItems(grid.getClassi());
            dialog.close();
        });
        Button chiudi = new Button("Chiudi");
        chiudi.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        chiudi.addClickListener(e -> dialog.close());
        buttonLayout.add(conferma, chiudi);

        dialog.add(buttonLayout);
    }

    public ClasseForm getForm() {
        return form;
    }

    public Details getDocentiDetails() {
        return docentiDetails;
    }

    public Details getStudentiDetails() {
        return studentiDetails;
    }

    public ListBox<String> getListBox() {
        return studentiList;
    }

    public MultiselectComboBox<Insegnamento> getMultiselectComboBox() {
        return insegnamentiCombo;
    }

    public void setGrid(ClasseGrid grid) {
        this.grid = grid;
    }


}
