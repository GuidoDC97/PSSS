package com.psss.registro.views.docenti;

import com.psss.registro.models.Docente;
import com.psss.registro.services.DocenteService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.psss.registro.views.main.MainView;

@Route(value = "docenti", layout = MainView.class)
@PageTitle("Docenti")
@CssImport("./styles/views/docenti/docenti-view.css")
public class DocentiView extends Div {

    private Grid<Docente> grid = new Grid<>(Docente.class);

    FormLayout formEdit = new FormLayout();
    FormLayout formAdd = new FormLayout();

//    Dialog dialogAdd = new Dialog(new H3("Nuovo docente"));
    Dialog dialogAdd = new Dialog(new Text("Nuovo docente"));

    private TextField nomeEdit = new TextField();
    private TextField cognomeEdit = new TextField();

    private TextField nomeAdd = new TextField();
    private TextField cognomeAdd = new TextField();

    private TextField filtro = new TextField();

    private Button aggiungi = new Button("Aggiungi");
    private Button conferma = new Button("Conferma");

    private Button aggiorna = new Button("Aggiorna");
    private Button elimina = new Button("Elimina");

    private Binder<Docente> binderEdit = new Binder<>(Docente.class);
    private Binder<Docente> binderAdd = new Binder<>(Docente.class);;

    private DocenteService docenteService;

    public DocentiView(DocenteService docenteService) {

        this.docenteService = docenteService;

        setId("docenti-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);      // primary: grid
        createEditorLayout(splitLayout);    // secondary: editor

        splitLayout.getSecondaryComponent().setVisible(false);
        add(splitLayout);

        updateGrid();

        createDialog();
        createEditBinder();
        createAddBinder();

        // Listeners
        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            populateForm(event.getValue());
            splitLayout.getSecondaryComponent().setVisible(!event.getHasValue().isEmpty());
        });

        aggiungi.addClickListener(event -> dialogAdd.open());

        conferma.addClickListener(e -> {
            addDocente();
            updateGrid();
            dialogAdd.close();
        });

        // the grid valueChangeEvent will clear the form too
        elimina.addClickListener(e -> {
            deleteDocente();
            updateGrid();
            grid.asSingleSelect().clear();
        });

        aggiorna.addClickListener(e -> {
            updateDocente();
            updateGrid();
            grid.asSingleSelect().clear();
        });
    }

    private void createGridLayout(SplitLayout splitLayout) {
        grid.setColumns("nome", "cognome");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

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
        toolBarLayout.setSpacing(true);

        filtro.setPlaceholder("Filtra per nome...");
        filtro.setClearButtonVisible(true);
        filtro.setValueChangeMode(ValueChangeMode.LAZY);
        filtro.addValueChangeListener(e-> updateGrid());

        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        toolBarLayout.add(filtro, aggiungi);

        wrapper.add(toolBarLayout);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");

        editorLayoutDiv.add(editorDiv);

        createFormEditLayout(editorDiv);
        createButtonEditLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createFormEditLayout(Div editorDiv) {
        nomeEdit.setClearButtonVisible(true);
        nomeEdit.getElement().getClassList().add("full-width");
        cognomeEdit.setClearButtonVisible(true);
        cognomeEdit.getElement().getClassList().add("full-width");
        formEdit.addFormItem(nomeEdit, "Nome");
        formEdit.addFormItem(cognomeEdit, "Cognome");
        editorDiv.add(formEdit);
    }

    private void createButtonEditLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);

        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR);
        aggiorna.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonLayout.add(aggiorna, elimina);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createDialog() {
        dialogAdd.setId("editor-layout");

        Div addDiv = new Div();
        addDiv.setId("editor");

        dialogAdd.add(addDiv);

        createFormAddLayout(addDiv);
        createButtonAddLayout(dialogAdd);
    }

    private void createFormAddLayout(Div addDiv) {
        nomeAdd.setClearButtonVisible(true);
        nomeAdd.getElement().getClassList().add("full-width");
        cognomeAdd.setClearButtonVisible(true);
        cognomeAdd.getElement().getClassList().add("full-width");
        formAdd.addFormItem(nomeAdd, "Nome");
        formAdd.addFormItem(cognomeAdd, "Cognome");
        formAdd.setSizeFull();
        addDiv.add(formAdd);
    }

    private void createButtonAddLayout(Dialog dialog) {
        HorizontalLayout confermaLayout = new HorizontalLayout();
        confermaLayout.setId("button-layout");
        confermaLayout.setWidthFull();
        confermaLayout.setSpacing(true);

        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        confermaLayout.add(conferma);
        dialog.add(conferma);
    }

    private void createEditBinder() {
        binderEdit.forField(nomeEdit)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(Docente::getNome, Docente::setNome);
        binderEdit.forField(cognomeEdit)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(Docente::getCognome, Docente::setCognome);

        binderEdit.addStatusChangeListener(e -> aggiorna.setEnabled(binderEdit.isValid()));
    }

    private void createAddBinder() {
        binderAdd.forField(nomeAdd)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(Docente::getNome, Docente::setNome);
        binderAdd.forField(cognomeAdd)
                .withValidator(new StringLengthValidator(
                        "Please add the first name", 1, null))
                .bind(Docente::getCognome, Docente::setCognome);

        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));

    }

    private void populateForm(Docente value) {
        // Value can be null as well, that clears the form
        binderEdit.readBean(value);
    }

    private void updateGrid() {
        grid.setItems(docenteService.findAll(filtro.getValue()));
    }

    private void addDocente() {
        Docente docente = new Docente(nomeAdd.getValue(), cognomeAdd.getValue());
        docenteService.createDocente(docente);
        Notification.show("Docente aggiunto con successo!");
    }

    private void deleteDocente(){
        Docente docente = grid.getSelectedItems().iterator().next();
        docenteService.deleteById(docente.getId());
//        docenteService.deleteByNomeAndCognome(nomeEdit.getValue(), cognomeEdit.getValue());
        Notification.show("Docente eliminato con successo!");
    }

    private void updateDocente() {
        Docente docenteUpdated = new Docente(nomeEdit.getValue(), cognomeEdit.getValue());
        Docente docente = grid.getSelectedItems().iterator().next();
        docenteService.updateDocente(docente, docenteUpdated);
        Notification.show("Docente aggiornato con successo!");
    }
}
