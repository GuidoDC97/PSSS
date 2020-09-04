package com.psss.registro.views.segretario;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Docente;
import com.psss.registro.models.Materia;
import com.psss.registro.models.Studente;
import com.psss.registro.services.ClasseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TODO: definire final i campi inizializzati fuori dal costruttore.
@Route(value = "docenti", layout = SegretarioMainView.class)
@PageTitle("Classi")
@CssImport("./styles/views/classi/classi-view.css")
public class ClassiView extends Div {

    public class AnnoValidator implements Validator {

        @Override
        public ValidationResult apply(Object o, ValueContext valueContext) {
            return null;
        }

        @Override
        public Object apply(Object o, Object o2) {
            return null;
        }
    }

    private final Grid<Classe> grid = new Grid<>(Classe.class);

    private final FormLayout formEdit = new FormLayout();
    private final FormLayout formAdd = new FormLayout();

    private final Dialog dialogAdd = new Dialog();
    private final Dialog dialogDel = new Dialog();

    private final ComboBox<Integer> annoEdit = new ComboBox<Integer> ();
    private final ComboBox<Character>  sezioneEdit = new ComboBox<Character> ();
    private final IntegerField annoScolasticoEdit = new IntegerField();

    private final ComboBox<Integer>  annoAdd = new ComboBox<Integer> ();
    private final ComboBox<Character>  sezioneAdd = new ComboBox<Character> ();
    private final IntegerField annoScolasticoAdd = new IntegerField();

    private final Details studentiDetails = new Details();
    private final ListBox<String> studentiList = new ListBox<>();
    private final Details materieDetails = new Details();
    private final ListBox<String> materieList = new ListBox<>();

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");
    private final Button conferma = new Button("Conferma");

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");
    private final Button confermaDel = new Button("Conferma");
    private final Button chiudiDel = new Button("Chiudi");

    private final Binder<Classe> binderEdit = new Binder<>(Classe.class);
    private final Binder<Classe> binderAdd = new Binder<>(Classe.class);

    private ClasseService classeService;
    private List<Classe> classi;

    public ClassiView(ClasseService classeService) {

        this.classeService = classeService;

        classi = classeService.findAll();

        setId("classi-view");

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
        grid.setColumns("anno", "sezione", "annoScolastico");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(classi);
        grid.asSingleSelect().addValueChangeListener(event -> {
            Classe classe = event.getValue();
            Component editor = splitLayout.getSecondaryComponent();

            populateForm(classe);

            editor.setVisible(!event.getHasValue().isEmpty());

            studentiDetails.setOpened(false);
            studentiList.setItems("");

            if(editor.isVisible()) {
                List<String> studenti = new LinkedList<>();
                for (Studente studente : classe.getStudenti()) {
                    studenti.add(studente.getNome() + " " + studente.getCognome());
                }
                studentiList.setItems(studenti);
                studentiDetails.setContent(studentiList);
            }

            materieDetails.setOpened(false);
            materieList.setItems("");

            if(editor.isVisible()) {
                List<String> materie = new LinkedList<>();
                for (Materia materia : classe.getMaterie()) {
                    materie.add(materia.getNome());
                }
                studentiList.setItems(materie);
                studentiDetails.setContent(studentiList);
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
        createStudentiDetails(editorDiv);
        createMaterieDetails(editorDiv);
        createButtonEditLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createFormEditLayout(Div editorDiv) {

        annoEdit.getElement().getClassList().add("full-width");
        annoEdit.setItems(1,2,3,4,5);
        sezioneEdit.getElement().getClassList().add("full-width");
        sezioneEdit.setItems('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
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

        Label text = new Label("Sei sicuro di voler eliminare una classe?");
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

    private void createStudentiDetails(Div editorDiv) {
        studentiDetails.setSummaryText("Studenti");
        studentiList.setReadOnly(true);
        editorDiv.add(studentiDetails);
    }

    private void createMaterieDetails(Div editorDiv) {
        materieDetails.setSummaryText("Materie");
        materieList.setReadOnly(true);
        editorDiv.add(materieDetails);
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
        annoAdd.setAutofocus(true);
        annoAdd.getElement().getClassList().add("full-width");
        annoAdd.setItems(1,2,3,4,5);
        sezioneAdd.getElement().getClassList().add("full-width");
        sezioneAdd.setItems('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
        annoScolasticoAdd.setClearButtonVisible(true);
        annoScolasticoAdd.getElement().getClassList().add("full-width");
        formAdd.addFormItem(annoAdd, "Anno");
        formAdd.addFormItem(sezioneAdd, "Sezione");
        formAdd.addFormItem(annoScolasticoAdd, "Anno scolastico");
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

    private void createEditBinder() {

        binderEdit.forField(annoEdit).asRequired("Selezionare l'anno").bind(Classe::getAnno, Classe::setAnno);
        binderEdit.forField(sezioneEdit).asRequired("Selezionare la sezione").bind(Classe::getSezione, Classe::setSezione);
        binderEdit.forField(annoScolasticoEdit).asRequired("Inserire l'anno scolastico")
                .bind(Classe::getAnnoScolastico, Classe::setAnnoScolastico);

        binderEdit.addStatusChangeListener(e -> aggiorna.setEnabled(binderEdit.isValid()));
    }

    private void createAddBinder() {
        binderAdd.forField(annoAdd).asRequired("Selezionare l'anno").bind(Classe::getAnno, Classe::setAnno);
        binderAdd.forField(sezioneAdd).asRequired("Selezionare la sezione").bind(Classe::getSezione, Classe::setSezione);
        binderAdd.forField(annoScolasticoAdd).asRequired("Inserire l'anno scolastico")
                .bind(Classe::getAnnoScolastico, Classe::setAnnoScolastico);

        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));
    }

    private void populateForm(Classe value) {
        binderEdit.readBean(value);
    }

    private void updateGrid() {
        grid.setItems(classi);
    }

    //TODO: gestire correttamente le liste che costituiscono la griglia come ha fatto Fabio.
    //TODO: gestire bene il costruttore
    private void addClasse() {
        Classe classe = classeService.createClasse(annoAdd.getValue(), sezioneAdd.getValue(), annoScolasticoAdd.getValue());
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
        boolean exist = false;
//        System.out.println(classeUpdated);
        for(Classe classeInList : classi){
            //System.out.println(classeInList.equals(classeUpdated));
//            System.out.println(classeInList);
//
//            if(classeInList.equals(classeUpdated)) {
//                Notification.show("Materia già esistente!");
//                exist = true;
//                break;
//            }
        }
        if(true){
            Classe classe = grid.getSelectedItems().iterator().next();
            classeService.updateClasse(classe, annoEdit.getValue(), sezioneEdit.getValue(), annoScolasticoEdit.getValue());
            Notification.show("Materia aggiornata con successo!");
        }

    }

}