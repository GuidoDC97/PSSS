package com.psss.registro.views.docenti;

import com.psss.registro.models.Docente;
import com.psss.registro.services.DocenteService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.psss.registro.views.main.MainView;


import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "docenti", layout = MainView.class)
@PageTitle("Docenti")
@CssImport("./styles/views/docenti/docenti-view.css")
public class DocentiView extends Div {

    private Grid<Docente> grid;

    private TextField nome = new TextField();
    private TextField cognome = new TextField();
    private TextField filtro = new TextField();


    private Button elimina = new Button("Elimina");
    private Button salva = new Button("Salva");

    private Binder<Docente> binder;

//    @Autowired
    private DocenteService docenteService;

    public DocentiView(DocenteService docenteService) {
        this.docenteService = docenteService;

        setId("docenti-view");
        // Configure Grid
        grid = new Grid<>(Docente.class);
        grid.setColumns("nome", "cognome");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));

        // Configure Form
        binder = new Binder<>(Docente.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

//        binder.forField(nome)
//                .withValidator((Validator<String>) (value, context) -> {
//                    if (value.isEmpty()) {
//                        return ValidationResult
//                                .error("Nome docente non valido!");
//                    }
//                    if (value.isBlank()) {
//                        return ValidationResult
//                                .error("Nome docente non valido!");
//                    }
//                    return ValidationResult.ok();
//                }).bind(Docente::getNome, Docente::setNome);
//
//        binder.forField(cognome)
//                .withValidator((Validator<String>) (value, context) -> {
//                    if (value.isEmpty()) {
//                        return ValidationResult
//                                .error("Nome docente non valido!");
//                    }
//                    if (value.isBlank()) {
//                        return ValidationResult
//                                .error("Nome docente non valido!");
//                    }
//                    return ValidationResult.ok();
//                }).bind(Docente::getCognome, Docente::setCognome);

        // the grid valueChangeEvent will clear the form too
        elimina.addClickListener(e -> {
            grid.asSingleSelect().clear();

            // Todo: correggere l'eliminazione (SELECT invece di DELETE)
            docenteService.deleteByNomeAndCognome(nome.getValue(), cognome.getValue());
            Notification.show("Docente eliminato con successo!");

            updateGrid();
        });

        salva.addClickListener(e -> {
            saveDocente();
            updateGrid();
        });

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        configureNome();
        configureCognome();
        configureFiltro();

        updateGrid();
    }

    private void configureNome() {
        nome.setClearButtonVisible(true);
    }

    private void configureCognome() {
        cognome.setClearButtonVisible(true);
    }

    private void configureFiltro() {
        filtro.setPlaceholder("Cerca per nome...");
        filtro.setClearButtonVisible(true);
        filtro.setValueChangeMode(ValueChangeMode.LAZY);
        filtro.addValueChangeListener(e-> updateGrid());
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        addFormItem(editorDiv, formLayout, nome, "Nome");
        addFormItem(editorDiv, formLayout, cognome, "Cognome");
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        elimina.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        salva.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(salva, elimina);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);

        wrapper.add(filtro, grid);
    }

    private void addFormItem(Div wrapper, FormLayout formLayout, AbstractField field, String fieldName) {
        formLayout.addFormItem(field, fieldName);
        wrapper.add(formLayout);
        field.getElement().getClassList().add("full-width");
    }

    private void populateForm(Docente value) {
        // Value can be null as well, that clears the form
        binder.readBean(value);
    }

    private void updateGrid() {
        grid.setItems(docenteService.findAll(filtro.getValue()));
    }

    private void saveDocente() {
        // Todo: validazione dei campi
        Docente docente = new Docente(nome.getValue(), cognome.getValue());
        docenteService.saveAndFlush(docente);
        System.out.println("DEBUG: " + docente.toString());
        Notification.show("Docente aggiunto con successo!");
    }
}
