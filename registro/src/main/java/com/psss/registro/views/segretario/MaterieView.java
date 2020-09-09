package com.psss.registro.views.segretario;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.MateriaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import java.util.*;
import java.util.stream.Collectors;


@Route(value = "segretario/materie", layout = SegretarioMainView.class)
@PageTitle("Materie")
@CssImport("./styles/views/materie/materie-view.css")
public class MaterieView extends Div {

    //TODO: sistemare bug di modifica del nome della materia
    private Grid<Materia> grid = new Grid<>(Materia.class);

    private final FormLayout formEdit = new FormLayout();
    private final FormLayout formAdd = new FormLayout();

    private final Dialog dialogAdd = new Dialog();
    private final Dialog dialogDel = new Dialog();

    private final TextField codiceEdit = new TextField();
    private final TextField nomemateriaEdit = new TextField();

    private final TextField codiceAdd = new TextField();
    private final TextField nomemateriaAdd = new TextField();

    private final Details docentiDetails = new Details();
    private final ListBox<String> docentiList = new ListBox<>();

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");
    private final Button conferma = new Button("Conferma");

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");
    private final Button confermaDel = new Button("Conferma");
    private final Button chiudiDel = new Button("Chiudi");

    private final Binder<Materia> binderEdit = new Binder<>(Materia.class);
    private final Binder<Materia> binderAdd = new Binder<>(Materia.class);

    private MateriaService materiaService;

    private List<Materia> materie;

    public MaterieView(MateriaService materiaService) {

        this.materiaService = materiaService;

        materie = materiaService.findAll();

        setId("materie-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);      // primary: grid
        createEditorLayout(splitLayout);    // secondary: editor

        splitLayout.getSecondaryComponent().setVisible(false);
        add(splitLayout);

        createAddDialog();
        createEditBinder();
        createAddBinder();
    }

    private void createGridLayout(SplitLayout splitLayout) {
        grid.setColumns("codice", "nome");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(materie);
        grid.asSingleSelect().addValueChangeListener(event -> {
            Materia materia = event.getValue();
            Component editor = splitLayout.getSecondaryComponent();

            populateForm(materia);

            editor.setVisible(!event.getHasValue().isEmpty());

            docentiDetails.setOpened(false);
            docentiList.setItems("");

            if(editor.isVisible()) {
                List<String> docenti = new ArrayList<>();
                for (Docente docente : materia.getDocenti()) {
                    docenti.add(docente.getNome() + " " + docente.getCognome());
                }
                docentiList.setItems(docenti);
                docentiDetails.setContent(docentiList);
            }
        });

        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();

        createToolbarLayout(wrapper);

        wrapper.add(grid);

        splitLayout.addToPrimary(wrapper);
    }

    private void createToolbarLayout(Div wrapper) {
        HorizontalLayout toolBarLayout = new HorizontalLayout();
        toolBarLayout.setId("button-layout");
        toolBarLayout.setWidthFull();
        toolBarLayout.setSpacing(false);
        toolBarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        filtro.setPlaceholder("Cerca...");
        filtro.setClearButtonVisible(true);
        filtro.setValueChangeMode(ValueChangeMode.LAZY);
        filtro.addValueChangeListener(event -> {
            Set<Materia> foundMateria = materie.stream()
                    .filter(materia -> materia.getCodice()
                            .startsWith(event.getValue().toUpperCase()) ||
                            materia.getNome().toLowerCase()
                                    .startsWith(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            grid.setItems(foundMateria);
        });

        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiungi.addClickListener(event -> dialogAdd.open());

        toolBarLayout.add(filtro, aggiungi);

        wrapper.add(toolBarLayout);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");

        Label titolo = new Label("Scheda materia");
        titolo.setClassName("bold-text-layout");
        editorDiv.add(titolo);

        editorLayoutDiv.add(editorDiv);

        createFormEditLayout(editorDiv);
        createDetails(editorDiv);
        createButtonEditLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createFormEditLayout(Div editorDiv) {
        codiceEdit.setClearButtonVisible(true);
        codiceEdit.getElement().getClassList().add("full-width");

        nomemateriaEdit.setClearButtonVisible(true);
        nomemateriaEdit.getElement().getClassList().add("full-width");

        formEdit.addFormItem(codiceEdit, "Codice");
        formEdit.addFormItem(nomemateriaEdit, "Nome");

        codiceEdit.addValueChangeListener(e->{
            codiceEdit.setValue(codiceEdit.getValue().toUpperCase());
        });

        editorDiv.add(formEdit);

        createDeleteDialog();
    }

    private void createDeleteDialog() {
        dialogDel.setCloseOnEsc(false);
        dialogDel.setCloseOnOutsideClick(false);

        Div delDiv = new Div();
        delDiv.setId("editor");

        Label text = new Label("Sei sicuro di voler eliminare una materia?");
        text.setClassName("text-layout");
        delDiv.add(text);

        dialogDel.add(delDiv);

        createButtonDelLayout(dialogDel);
    }

    private void createButtonDelLayout(Dialog dialogDel) {
        HorizontalLayout confermaLayout = new HorizontalLayout();
        confermaLayout.setId("button-layout");
        confermaLayout.setWidthFull();
        confermaLayout.setSpacing(false);
        confermaLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        confermaDel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confermaDel.addClickListener(e -> {
            deleteMateria();
            updateGrid();
            grid.asSingleSelect().clear();
            dialogDel.close();
        });
        chiudiDel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        chiudiDel.addClickListener(e -> {
            dialogDel.close();
        });

        confermaLayout.add(confermaDel, chiudiDel);
        dialogDel.add(confermaLayout);
    }

    private void createDetails(Div editorLayoutDiv) {
        docentiDetails.setSummaryText("Docenti");
        docentiList.setReadOnly(true);

        editorLayoutDiv.add(docentiDetails);
    }

    private void createButtonEditLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(false);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR);
        elimina.addClickListener(event -> dialogDel.open());

        aggiorna.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiorna.addClickShortcut(Key.ENTER).listenOn(editorLayoutDiv);
        aggiorna.addClickListener(e -> {
            updateMateria();
            updateGrid();
            grid.asSingleSelect().clear();
        });

        buttonLayout.add(aggiorna, elimina);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createAddDialog() {
        dialogAdd.setId("editor-layout");

        Label titolo = new Label("Nuova materia");
        titolo.setClassName("bold-text-layout");
        dialogAdd.add(titolo);
        Div addDiv = new Div();
        addDiv.setId("editor");
        dialogAdd.add(addDiv);

        createFormAddLayout(addDiv);
        createButtonAddLayout(dialogAdd, addDiv);

        dialogAdd.setCloseOnEsc(true);
        dialogAdd.addOpenedChangeListener(e -> {
            if(!e.isOpened()) {
                binderAdd.readBean(null);
            }
        });
    }

    private void createFormAddLayout(Div addDiv) {
        codiceAdd.setClearButtonVisible(true);
        codiceAdd.setAutofocus(true);
        codiceAdd.getElement().getClassList().add("full-width");
        codiceAdd.addValueChangeListener(e->{
            codiceAdd.setValue(codiceAdd.getValue().toUpperCase());
        });

        nomemateriaAdd.setClearButtonVisible(true);
        nomemateriaAdd.getElement().getClassList().add("full-width");

        formAdd.addFormItem(codiceAdd, "Codice");
        formAdd.addFormItem(nomemateriaAdd, "Nome Materia");
        formAdd.setSizeFull();

        addDiv.add(formAdd);
    }

    private void createButtonAddLayout(Dialog dialogAdd, Div addDiv) {
        HorizontalLayout confermaLayout = new HorizontalLayout();
        confermaLayout.setId("button-layout");
        confermaLayout.setWidthFull();
        confermaLayout.setSpacing(true);
        conferma.setEnabled(false);
        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        conferma.addClickShortcut(Key.ENTER).listenOn(addDiv);
        conferma.addClickListener(e -> {
            addMateria();
            updateGrid();
            dialogAdd.close();
        });

        confermaLayout.add(conferma);
        dialogAdd.add(confermaLayout);
    }

    private void createEditBinder() {
        binderEdit.forField(codiceEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il codice", 1, null))
                .bind(Materia::getCodice, Materia::setCodice);
        binderEdit.forField(nomemateriaEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il nome", 1, null))
                .bind(Materia::getNome, Materia::setNome);

        binderEdit.addStatusChangeListener(e -> aggiorna.setEnabled(binderEdit.isValid()));
    }

    private void createAddBinder() {
        binderAdd.forField(codiceAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire il codice", 1, null))
                .bind(Materia::getCodice, Materia::setCodice);
        binderAdd.forField(nomemateriaAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire il nome della materia", 1, null))
                .bind(Materia::getNome, Materia::setNome);

        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));
    }

    private void populateForm(Materia value) {
        binderEdit.readBean(value);
    }

    private void updateGrid() {
        grid.setItems(materie);
    }

    private void addMateria() {
        boolean exist = false;
        for(Materia materiaInList : materie){
            if(materiaInList.getCodice().equals(codiceAdd.getValue())) {
                Notification.show("Materia già esistente!");
                exist = true;
                break;
            }
        }
        if(!exist){
            Materia materia = materiaService.createMateria(codiceAdd.getValue(), nomemateriaAdd.getValue());
            materie.add(materia);
            Notification.show("Materia aggiunta con successo!");
        }
    }

    private void deleteMateria(){
        Materia materia = grid.getSelectedItems().iterator().next();

        boolean exist = false;
        for(Materia materiaInList : materie){
            if(materiaInList.getCodice().equals(materia.getCodice())) {
                materiaService.deleteById(materia.getId());
                materie.remove(materia);
                Notification.show("Materia eliminata con successo!");
                exist = true;
                break;
            }
        }
        if(!exist){
            Notification.show("Materia inesistente!");
        }
    }

    private void updateMateria() {
        boolean exist = false;
        for(Materia materiaInList : materie){
            if(materiaInList.getCodice().equals(codiceEdit.getValue())) {
                Notification.show("Materia già esistente!");
                exist = true;
                break;
            }
        }
        if(!exist){
            Materia materia = grid.getSelectedItems().iterator().next();
            materiaService.updateMateria(materia, codiceEdit.getValue(), nomemateriaEdit.getValue());
            Notification.show("Materia aggiornata con successo!");
        }
    }
}

