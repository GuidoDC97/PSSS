package com.psss.registro.ui.segretario.components;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.MateriaService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MateriaEditor extends Div {

    private final MateriaForm form = new MateriaForm();

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");

    private final Details details = new Details();

    private final ListBox<Docente> listBox = new ListBox<>();

    private final Dialog dialog = new Dialog();

    private MateriaGrid grid;

    private MateriaService materiaService;

    public MateriaEditor(MateriaService materiaService) {
        setId("editor-layout");

        this.materiaService = materiaService;

        Label titolo = new Label("Scheda materia");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        add(formDiv, details, createButtonLayout());

        createDetails();

        createDialog();
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
        elimina.addClickListener(event -> dialog.open());

        aggiorna.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiorna.addClickShortcut(Key.ENTER).listenOn(this);
        aggiorna.addClickListener(event -> {
            Materia materia = grid.getGrid().getSelectedItems().iterator().next();
            form.getBinder().writeBeanIfValid(materia);
//            materiaService.update(materia);
            System.out.println("Materia aggiornata: " + materia.toString());
            grid.getGrid().setItems(grid.getMaterie());
        });

        buttonLayout.add(aggiorna, elimina);

        return buttonLayout;
    }

    private void createDialog() {
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
//            materiaService.delete(materia);
            System.out.println("Materia eliminata: " + materia.toString());
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
