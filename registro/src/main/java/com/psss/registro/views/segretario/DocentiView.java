package com.psss.registro.views.segretario;

import com.psss.registro.models.Docente;
import com.psss.registro.models.Materia;
import com.psss.registro.services.DocenteService;
import com.psss.registro.services.MateriaService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route(value = "segretario/docenti", layout = SegretarioMainView.class)
@PageTitle("Docenti")
@CssImport("./styles/views/docenti/docenti-view.css")
public class DocentiView extends Div {

    private final Grid<Docente> grid = new Grid<>(Docente.class);

    private final FormLayout formEdit = new FormLayout();
    private final FormLayout formAdd = new FormLayout();

    private final Dialog dialogAdd = new Dialog();
    private final Dialog dialogDel = new Dialog();

    private final TextField nomeEdit = new TextField();
    private final TextField cognomeEdit = new TextField();
    private final TextField codiceFiscaleEdit = new TextField();
    private final ComboBox<Character> sessoEdit = new ComboBox<>();
    private final DatePicker dataEdit = new DatePicker();
    private final EmailField emailEdit = new EmailField();
    private final TextField telefonoEdit = new TextField();
    private final MultiselectComboBox<Materia> materieEdit = new MultiselectComboBox<>();

    private final TextField nomeAdd = new TextField();
    private final TextField cognomeAdd = new TextField();
    private final TextField codiceFiscaleAdd = new TextField();
    private final ComboBox<Character> sessoAdd = new ComboBox<>();
    private final DatePicker dataAdd = new DatePicker();
    private final EmailField emailAdd = new EmailField();
    private final TextField telefonoAdd = new TextField();
    private final MultiselectComboBox<Materia> materieAdd = new MultiselectComboBox<>();

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");
    private final Button conferma = new Button("Conferma");

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");
    private final Button confermaDel = new Button("Conferma");
    private final Button chiudiDel = new Button("Chiudi");

    private final Binder<Docente> binderEdit = new Binder<>(Docente.class);
    private final Binder<Docente> binderAdd = new Binder<>(Docente.class);

    private DocenteService docenteService;
    private MateriaService materiaService;
    private List<Docente> docenti;

    public DocentiView(DocenteService docenteService, MateriaService materiaService) {
        this.materiaService = materiaService;
        this.docenteService = docenteService;
        setId("docenti-view");

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
        grid.setColumns("nome", "cognome", "codiceFiscale", "sesso", "data", "username", "telefono");
        grid.getColumnByKey("username").setHeader("E-mail");
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
            Set<Docente> foundDocenti = docenti.stream()
                    .filter(docente -> docente.getNome().toLowerCase()
                            .startsWith(event.getValue().toLowerCase()) ||
                            docente.getCognome().toLowerCase()
                            .startsWith(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            grid.setItems(foundDocenti);
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

        Label titolo = new Label("Scheda docente");
        titolo.setClassName("bold-text-layout");
        editorDiv.add(titolo);

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

        codiceFiscaleEdit.setClearButtonVisible(true);
        codiceFiscaleEdit.getElement().getClassList().add("full-width");

        sessoEdit.setItems('M','F');
        sessoEdit.getElement().getClassList().add("full-width");

        dataEdit.getElement().getClassList().add("full-width");

        emailEdit.setClearButtonVisible(true);
        emailEdit.getElement().getClassList().add("full-width");

        telefonoEdit.setClearButtonVisible(true);
        telefonoEdit.getElement().getClassList().add("full-width");

        materieEdit.setItems(materiaService.findAll());
        materieEdit.setItemLabelGenerator(Materia::getNome);
        materieEdit.getElement().getClassList().add("full-width");

        formEdit.addFormItem(nomeEdit, "Nome");
        formEdit.addFormItem(cognomeEdit, "Cognome");
        formEdit.addFormItem(codiceFiscaleEdit, "Codice Fiscale");
        formEdit.addFormItem(sessoEdit, "Sesso");
        formEdit.addFormItem(dataEdit, "Data");
        formEdit.addFormItem(emailEdit, "E-mail");
        formEdit.addFormItem(telefonoEdit, "Telefono");
        formEdit.addFormItem(materieEdit, "Materie");

        editorDiv.add(formEdit);

        createDeleteDialog();
    }

    private void createDeleteDialog() {
        dialogDel.setCloseOnEsc(false);
        dialogDel.setCloseOnOutsideClick(false);

        Div delDiv = new Div();
        delDiv.setId("editor");

        Label text = new Label("Sei sicuro di voler eliminare un docente?");
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
            deleteDocente();
            updateGrid();
            grid.asSingleSelect().clear();
            dialogDel.close();
        });
        chiudiDel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        chiudiDel.addClickListener(e -> dialogDel.close());

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
            updateDocente();
            updateGrid();
            grid.asSingleSelect().clear();
        });

        buttonLayout.add(aggiorna, elimina);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createAddDialog() {
        dialogAdd.setId("editor-layout");

        Label titolo = new Label("Nuovo docente");
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
        nomeAdd.setClearButtonVisible(true);
        nomeAdd.setAutofocus(true);
        nomeAdd.getElement().getClassList().add("full-width");

        cognomeAdd.setClearButtonVisible(true);
        cognomeAdd.getElement().getClassList().add("full-width");

        codiceFiscaleAdd.setClearButtonVisible(true);
        codiceFiscaleAdd.getElement().getClassList().add("full-width");

        sessoAdd.setItems('M','F');
        sessoAdd.getElement().getClassList().add("full-width");

        dataAdd.getElement().getClassList().add("full-width");

        emailAdd.setClearButtonVisible(true);
        emailAdd.getElement().getClassList().add("full-width");

        telefonoAdd.setClearButtonVisible(true);
        telefonoAdd.getElement().getClassList().add("full-width");

        materieAdd.setItems(materiaService.findAll());
        materieAdd.setItemLabelGenerator(Materia::getNome);
        materieAdd.getElement().getClassList().add("full-width");


        formAdd.addFormItem(nomeAdd, "Nome");
        formAdd.addFormItem(cognomeAdd, "Cognome");
        formAdd.addFormItem(codiceFiscaleAdd, "Codice Fiscale");
        formAdd.addFormItem(sessoAdd, "Sesso");
        formAdd.addFormItem(dataAdd, "Data");
        formAdd.addFormItem(emailAdd, "E-mail");
        formAdd.addFormItem(telefonoAdd, "Telefono");
        formAdd.addFormItem(materieAdd, "Materie");

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
            addDocente();
            updateGrid();
            dialogAdd.close();
        });

        confermaLayout.add(conferma);
        dialogAdd.add(confermaLayout);
    }

    private void createEditBinder() {
        binderEdit.forField(nomeEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il nome", 1, null))
                .bind(Docente::getNome, Docente::setNome);
        binderEdit.forField(cognomeEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il cognome", 1, null))
                .bind(Docente::getCognome, Docente::setCognome);
        binderEdit.forField(codiceFiscaleEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire un codice fiscale di 16 caratteri", 16, 16))
                .withValidator(new Validator<String>() {
                    @Override
                    public ValidationResult apply(String s, ValueContext valueContext) {
                        boolean flag = true;
                        for(int i= 0; i<s.length() && flag;i++){
                            if((i>=0 && i<=5) || (i==8) || (i==11) || (i==15)){
                                flag = Character.isLetter(s.charAt(i));
                            } else {
                                flag = Character.isDigit(s.charAt(i));
                            }
                        }
                        if (flag) {
                            return ValidationResult.ok();
                        } else return ValidationResult.error("Inserire un codice fiscale valido");
                    }
                })
                .bind(Docente::getCodiceFiscale, Docente::setCodiceFiscale);
        binderEdit.forField(sessoEdit)
                .asRequired("Selezionare il sesso")
                .bind(Docente::getSesso, Docente::setSesso);
        binderEdit.forField(dataEdit)
                .asRequired("Selezionare la data di nascita")
                .bind(Docente::getData, Docente::setData);
        binderEdit.forField(emailEdit)
                .withValidator(new EmailValidator(
                        "Inserire la e-mail"))
                .bind(Docente::getUsername, Docente::setUsername);
        binderEdit.forField(telefonoEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il numero di telefono", 10, 10))
                .withValidator((new Validator<String>() {
                    @Override
                    public ValidationResult apply(String s, ValueContext valueContext) {
                        boolean flag = true;
                        for(int i = 0; i<s.length() && flag;i++){
                            flag = Character.isDigit(s.charAt(i));
                        }
                        if (flag){
                            return ValidationResult.ok();
                        } else return ValidationResult.error("Inserire un numero di telefono valido");
                    }
                }))
                .bind(Docente::getTelefono, Docente::setTelefono);
        binderEdit.forField(materieEdit)
                .bind(Docente::getMaterie, Docente::setMaterie);

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
        binderAdd.forField(codiceFiscaleAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire un codice fiscale di 16 caratteri", 16, 16))
                .withValidator(new Validator<String>() {
                    @Override
                    public ValidationResult apply(String s, ValueContext valueContext) {
                        boolean flag = true;
                        for(int i= 0; i<s.length() && flag;i++){
                            if((i>=0 && i<=5) || (i==8) || (i==11) || (i==15)){
                                flag = Character.isLetter(s.charAt(i));
                            } else {
                                flag = Character.isDigit(s.charAt(i));
                            }
                        }
                        if (flag) {
                            return ValidationResult.ok();
                        } else return ValidationResult.error("Inserire un codice fiscale valido");
                    }
                })
                .bind(Docente::getCodiceFiscale, Docente::setCodiceFiscale);
        binderAdd.forField(sessoAdd)
                .asRequired("Selezionare il sesso")
                .bind(Docente::getSesso, Docente::setSesso);
        binderAdd.forField(dataAdd)
                .asRequired("Selezionare la data di nascita")
                .bind(Docente::getData, Docente::setData);
        binderAdd.forField(emailAdd)
                .withValidator(new EmailValidator(
                        "Inserire una e-mail valida"))
                .bind(Docente::getUsername, Docente::setUsername);
        binderAdd.forField(telefonoAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire il numero di telefono", 10, 10))
                .withValidator((new Validator<String>() {
                    @Override
                    public ValidationResult apply(String s, ValueContext valueContext) {
                        boolean flag = true;
                        for(int i = 0; i<s.length() && flag;i++){
                            flag = Character.isDigit(s.charAt(i));
                        }
                        if (flag){
                            return ValidationResult.ok();
                        } else return ValidationResult.error("Inserire un numero di telefono valido");
                    }
                }))
                .bind(Docente::getTelefono, Docente::setTelefono);
        binderAdd.forField(materieAdd)
                .bind(Docente::getMaterie, Docente::setMaterie);

        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));
    }

    private void populateForm(Docente value) {
        // Value can be null as well, that clears the form
        binderEdit.readBean(value);
    }

    private void initGrid(){
        docenti = docenteService.findAll();
        grid.setItems(docenti);
    }

    private void updateGrid() {
        //grid.setPageSize(2);
        grid.setItems(docenti);
    }

    private void addDocente() {
        Docente docente = new Docente(nomeAdd.getValue(), cognomeAdd.getValue(), codiceFiscaleAdd.getValue(),
                sessoAdd.getValue(), dataAdd.getValue(), emailAdd.getValue(), telefonoAdd.getValue());
        docenteService.createDocente(docente, materieAdd.getSelectedItems());
        // TODO: far controllare a Guido la creazione del docente
        docenti.add(docente);
        Notification.show("Docente aggiunto con successo!");
    }

    private void deleteDocente(){
        Docente docente = grid.getSelectedItems().iterator().next();
        docenteService.deleteById(docente.getId());
        docenti.remove(docente);
        Notification.show("Docente eliminato con successo!");
    }

//  TODO: update di docenti gestito diversamente, decidere quale dei due modi lasciare
    private void updateDocente() {
        Docente docenteTemp = new Docente(nomeEdit.getValue(), cognomeEdit.getValue(), codiceFiscaleEdit.getValue(),
                sessoEdit.getValue(), dataEdit.getValue(), emailEdit.getValue(), telefonoEdit.getValue());
        Docente docente = grid.getSelectedItems().iterator().next();
        Docente docenteUpdated = docenteService.updateDocente(docente, docenteTemp, materieEdit.getSelectedItems());

        docenti.remove(docente);
        docenti.add(docenteUpdated);
        Notification.show("Docente aggiornato con successo!");
    }
}

//TODO: implementare callback per ACK operazioni su DB?