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
import com.vaadin.flow.component.html.H3;
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

@Route(value = "docenti", layout = MainView.class)
@PageTitle("Docenti")
@CssImport("./styles/views/docenti/docenti-view.css")
public class DocentiView extends Div {

    private Grid<Docente> grid;
    FormLayout formLayout;
    FormLayout formAdd;

    private TextField nome = new TextField();
    private TextField cognome = new TextField();

    private TextField nomeAdd = new TextField();
    private TextField cognomeAdd = new TextField();

    private TextField filtro = new TextField();

    private Button elimina = new Button("Elimina");
    private Button aggiungi = new Button("Aggiungi");
    private Button aggiorna = new Button("Aggiorna");

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

        // Configure Form
        binder = new Binder<>(Docente.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        splitLayout.getSecondaryComponent().setVisible(false);

        add(splitLayout);

        configureNome();
        configureCognome();
        configureFiltro();

        updateGrid();

        Dialog dialog = new Dialog(new H3("Nuovo docente"));
        dialog.setId("editor-layout");

        Div addDiv = new Div();
        addDiv.setId("editor");

        formAdd = new FormLayout();
        addFormItem(addDiv, formAdd, nomeAdd, "Nome");
        addFormItem(addDiv, formAdd, cognomeAdd, "Cognome");
        formAdd.setSizeFull();

        Button conferma = new Button("Conferma");
        HorizontalLayout confermaLayout = new HorizontalLayout();
        confermaLayout.setId("button-layout");
        confermaLayout.setWidthFull();
        confermaLayout.setSpacing(true);
        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confermaLayout.add(conferma);

        dialog.add(addDiv, conferma);

        aggiungi.addClickListener(event -> dialog.open());

        // Listeners
        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            populateForm(event.getValue());
            if(event.getHasValue().isEmpty()) {
                splitLayout.getSecondaryComponent().setVisible(false);
            } else {
                splitLayout.getSecondaryComponent().setVisible(true);
            }
        });

        // the grid valueChangeEvent will clear the form too
        elimina.addClickListener(e -> {
            deleteDocente();
            updateGrid();
            grid.asSingleSelect().clear();
        });

        conferma.addClickListener(e -> {
            addDocente();
            updateGrid();
            dialog.close();
        });

        aggiorna.addClickListener(e -> {
            updateDocente();
            updateGrid();
            grid.asSingleSelect().clear();
        });
    }

    private void configureNome() {
        nome.setClearButtonVisible(true);
    }

    private void configureCognome() {
        cognome.setClearButtonVisible(true);
    }

    private void configureFiltro() {
        filtro.setPlaceholder("Filtra per nome...");
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

        formLayout = new FormLayout();
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
        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR);
        aggiorna.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(aggiorna, elimina);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);

        HorizontalLayout filtroLayout = new HorizontalLayout();
        filtroLayout.setId("button-layout");
        filtroLayout.setWidthFull();
        filtroLayout.setSpacing(true);
        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        filtroLayout.add(filtro, aggiungi);
        wrapper.add(filtroLayout, grid);
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

    private void addDocente() {
        Docente docente = new Docente(nomeAdd.getValue(), cognomeAdd.getValue());
        docenteService.saveAndFlush(docente);
        Notification.show("Docente aggiunto con successo!");
    }

    private void deleteDocente(){
        docenteService.deleteByNomeAndCognome(nome.getValue(), cognome.getValue());
        Notification.show("Docente eliminato con successo!");
    }

    private void updateDocente() {
        Docente docente = new Docente(nome.getValue(), cognome.getValue());
        docenteService.saveAndFlush(docente);
        System.out.println("DEBUG: " + docente.toString());
        Notification.show("Docente aggiornato con successo!");
    }
}
