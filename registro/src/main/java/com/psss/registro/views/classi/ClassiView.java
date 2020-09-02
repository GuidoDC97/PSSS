package com.psss.registro.views.classi;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Docente;
import com.psss.registro.models.Materia;
import com.psss.registro.services.ClasseService;
import com.psss.registro.services.DocenteService;
import com.psss.registro.views.main.MainView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: definire final i campi inizializzati fuori dal costruttore.
@Route(value = "docenti", layout = MainView.class)
@PageTitle("Classi")
@CssImport("./styles/views/classi/classi-view.css")
public class ClassiView extends Div {

    public class Validator extends com.vaadin.flow.data.binder.Validator (){
        
    }


    private Grid<Classe> grid = new Grid<>(Classe.class);

    FormLayout formEdit = new FormLayout();
    FormLayout formAdd = new FormLayout();

    Dialog dialogAdd = new Dialog();
    Dialog dialogDel = new Dialog();

    private TextField annoEdit = new TextField();
    private TextField sezioneEdit = new TextField();
    private TextField annoScolasticoEdit = new TextField();
    //TODO AGGIUNGERE MATERIE CON IL DROP

    private TextField annoAdd = new TextField();
    private TextField sezioneAdd = new TextField();
    private TextField annoScolasticoAdd = new TextField();

    private TextField filtro = new TextField();

    private Button aggiungi = new Button("Aggiungi");
    private Button conferma = new Button("Conferma");

    private Button aggiorna = new Button("Aggiorna");
    private Button elimina = new Button("Elimina");
    private Button confermaDel = new Button("Conferma");
    private Button chiudiDel = new Button("Chiudi");

    private Binder<Classe> binderEdit = new Binder<>(Classe.class);
    private Binder<Classe> binderAdd = new Binder<>(Classe.class);

    private ClasseService classeService;
    private List<Classe> classi;

    public ClassiView(ClasseService classeService) {

        this.classeService = classeService;
        setId("classi-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);      // primary: grid
        createEditorLayout(splitLayout);    // secondary: editor

        splitLayout.getSecondaryComponent().setVisible(false);
        add(splitLayout);

        initGrid();

        createAddDialog();
        createEditBinder();
        createAddBinder();
    }

    private void createGridLayout(SplitLayout splitLayout) {
        grid.setColumns("anno", "sezione", "annoScolastico");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            populateForm(event.getValue());
            splitLayout.getSecondaryComponent().setVisible(!event.getHasValue().isEmpty());
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
            Set<Classe> foundClassi = classi.stream()
                    .filter(classe -> classe.getAnno() == Integer.parseInt(event.getValue()) ||
                    classe.getSezione() == Integer.parseInt(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            grid.setItems(foundClassi);
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

        Label titolo = new Label("Scheda classe");
        titolo.setClassName("bold-text-layout");
        editorDiv.add(titolo);

        editorLayoutDiv.add(editorDiv);

        createFormEditLayout(editorDiv);
        createButtonEditLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createFormEditLayout(Div editorDiv) {
        annoEdit.setClearButtonVisible(true);
        annoEdit.getElement().getClassList().add("full-width");
        sezioneEdit.setClearButtonVisible(true);
        sezioneEdit.getElement().getClassList().add("full-width");
        annoScolasticoEdit.setClearButtonVisible(true);
        annoScolasticoEdit.getElement().getClassList().add("full-width");
        formEdit.addFormItem(annoEdit, "Anno");
        formEdit.addFormItem(sezioneEdit, "Sezione");
        formEdit.addFormItem(annoScolasticoEdit,"Anno scolastico");
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
//        confermaDel.addClickShortcut(Key.ENTER).listenOn(confermaLayout); //bugga
        confermaDel.addClickListener(e -> {
            deleteClasse();
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
            updateClasse();
            updateGrid();
            grid.asSingleSelect().clear();
        });

        buttonLayout.add(aggiorna, elimina);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createAddDialog() {
        dialogAdd.setId("editor-layout");

        Label titolo = new Label("Nuova classe");
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
        annoAdd.setClearButtonVisible(true);
        annoAdd.setAutofocus(true);
        annoAdd.getElement().getClassList().add("full-width");
        sezioneAdd.setClearButtonVisible(true);
        sezioneAdd.getElement().getClassList().add("full-width");
        annoScolasticoAdd.setClearButtonVisible(true);
        annoScolasticoAdd.getElement().getClassList().add("full-width");
        formAdd.addFormItem(annoAdd, "Anno");
        formAdd.addFormItem(sezioneAdd, "Sezione");
        formAdd.addFormItem(annoScolasticoAdd, "Anno scolastico")
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
            addClasse();
            updateGrid();
            dialogAdd.close();
        });

        confermaLayout.add(conferma);
        dialogAdd.add(confermaLayout);
    }
    //<--------------------------------------------------------------------------------------------------------
    //TODO: studiare i validator.
    private void createEditBinder() {
        binderEdit.forField(annoEdit)
                .withValidator(new IntegerRangeValidator("Inserire un anno valido",1,5))
                .bind(Docente::getNome, Docente::setNome);
        binderEdit.forField(cognomeEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il cognome", 1, null))
                .bind(Docente::getCognome, Docente::setCognome);

        binderEdit.addStatusChangeListener(e -> aggiorna.setEnabled(binderEdit.isValid()));
    }

    private void createAddBinder() {
        binderAdd.forField(nomeAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire il nome", 1, null))
                .bind(Docente::getNome, Docente::setNome);
        binderAdd.forField(cognomeAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire il cognome", 1, null))
                .bind(Docente::getCognome, Docente::setCognome);

        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));
    }

    private void populateForm(Classe value) {
        // Value can be null as well, that clears the form
        binderEdit.readBean(value);
    }

    private void initGrid(){
        classi = classeService.findAll();
        grid.setItems(classi);
    }

    private void updateGrid() {
        //grid.setPageSize(2);
        grid.setItems(classi);
    }


    //TODO: gestire bene il costruttore
    private void addClasse() {
        Classe classe = new Classe(Integer.parseInt(annoAdd.getValue()), Character.valueOf(sezioneAdd.getValue().charAt(0)), Integer.parseInt(annoScolasticoAdd.getValue()), new ArrayList<Materia>());
        classeService.createClasse(classe);
        classi.add(classe);
        Notification.show("Classe aggiunta con successo!");
    }

    private void deleteClasse(){
        Classe classe = grid.getSelectedItems().iterator().next();
        classeService.deleteById(classe.getId());
        classi.remove(classe);
        Notification.show("Classe eliminata con successo!");
    }

    private void updateClasse() {
        Classe classeUpdated = new Classe(Integer.parseInt(annoAdd.getValue()), Character.valueOf(sezioneAdd.getValue().charAt(0)), Integer.parseInt(annoScolasticoAdd.getValue()), new ArrayList<Materia>());
        Classe classe = grid.getSelectedItems().iterator().next();
        classeService.updateClasse(classe, classeUpdated);
        classi.remove(classe);
        classi.add(classeUpdated);
        Notification.show("Classe aggiornata con successo!");
    }

}
