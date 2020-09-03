package com.psss.registro.views.segretario;

import com.psss.registro.models.Studente;
import com.psss.registro.services.StudenteService;
import com.psss.registro.views.segretario.SegretarioMainView;
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
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//TODO: fetchare i docenti dalla lista della griglia


@Route(value = "segretario/studenti", layout = SegretarioMainView.class)
@PageTitle("Studenti")
@CssImport("./styles/views/studenti/studenti-view.css")
public class StudentiView extends Div {

    private final Grid<Studente> grid = new Grid<>(Studente.class);

    FormLayout formEdit = new FormLayout();
    FormLayout formAdd = new FormLayout();

    Dialog dialogAdd = new Dialog();
    Dialog dialogDel = new Dialog();

    private final TextField nomeEdit = new TextField();
    private final TextField cognomeEdit = new TextField();
    private final TextField codiceFiscaleEdit = new TextField();
    private final EmailField emailEdit = new EmailField();
    private final DatePicker dataNascitaEdit = new DatePicker();
    private final ComboBox<Character> sessoEdit = new ComboBox<>();
    private final TextField numeroTelefonoEdit = new TextField();
    private final ComboBox<String> classeEdit = new ComboBox<>();

    private final TextField nomeAdd = new TextField();
    private final TextField cognomeAdd = new TextField();
    private final TextField codiceFiscaleAdd = new TextField();
    private final EmailField emailAdd = new EmailField();
    private final DatePicker dataNascitaAdd = new DatePicker();
    private final ComboBox<Character> sessoAdd = new ComboBox<>();
    private final TextField numeroTelefonoAdd = new TextField();
    private final ComboBox<String> classeAdd = new ComboBox<>();

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");
    private final Button conferma = new Button("Conferma");

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");
    private final Button confermaDel = new Button("Conferma");
    private final Button chiudiDel = new Button("Chiudi");

    private final Binder<Studente> binderEdit = new Binder<>(Studente.class);
    private final Binder<Studente> binderAdd = new Binder<>(Studente.class);

    private StudenteService studenteService;
    private List<Studente> studenti;

    public StudentiView(StudenteService studenteService) {

        this.studenteService = studenteService;
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
            Set<Studente> foundStudenti = studenti.stream()
                    .filter(studente -> studente.getNome().toLowerCase()
                            .startsWith(event.getValue().toLowerCase()) ||
                            studente.getCognome().toLowerCase()
                                    .startsWith(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            grid.setItems(foundStudenti);
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

        Label titolo = new Label("Scheda studente");
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
        codiceFiscaleEdit.addValueChangeListener(e->{
            codiceFiscaleEdit.setValue(codiceFiscaleEdit.getValue().toUpperCase());
        });
        codiceFiscaleEdit.getElement().getClassList().add("full-width");
        emailEdit.setClearButtonVisible(true);
        emailEdit.getElement().getClassList().add("full-width");
        dataNascitaEdit.getElement().getClassList().add("full-width");
        sessoEdit.getElement().getClassList().add("full-width");
        sessoEdit.setItems('M','F');
        numeroTelefonoEdit.setClearButtonVisible(true);
        numeroTelefonoEdit.getElement().getClassList().add("full-width");
        classeEdit.getElement().getClassList().add("full-width");

        formEdit.addFormItem(nomeEdit, "Nome");
        formEdit.addFormItem(cognomeEdit, "Cognome");
        formEdit.addFormItem(codiceFiscaleEdit, "Codice Fiscale");
        formEdit.addFormItem(emailEdit, "Email");
        formEdit.addFormItem(dataNascitaEdit, "Data di nascita");
        formEdit.addFormItem(sessoEdit, "Sesso");
        formEdit.addFormItem(numeroTelefonoEdit,"Telefono");

        editorDiv.add(formEdit);

        createDeleteDialog();
    }

    private void createDeleteDialog() {
        dialogDel.setCloseOnEsc(false);
        dialogDel.setCloseOnOutsideClick(false);

        Div delDiv = new Div();
        delDiv.setId("editor");

        Label text = new Label("Sei sicuro di voler eliminare uno studente?");
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
            deleteStudente();
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
            updateStudente();
            updateGrid();
            grid.asSingleSelect().clear();
        });

        buttonLayout.add(aggiorna, elimina);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createAddDialog() {
        dialogAdd.setId("editor-layout");

        Label titolo = new Label("Nuovo studente");
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
        codiceFiscaleAdd.addValueChangeListener(e->{
            codiceFiscaleAdd.setValue(codiceFiscaleAdd.getValue().toUpperCase());
        });
        codiceFiscaleAdd.getElement().getClassList().add("full-width");
        emailAdd.setClearButtonVisible(true);
        emailAdd.getElement().getClassList().add("full-width");
        dataNascitaAdd.getElement().getClassList().add("full-width");
        sessoAdd.getElement().getClassList().add("full-width");
        sessoAdd.setItems('M','F');
        numeroTelefonoAdd.setClearButtonVisible(true);
        numeroTelefonoAdd.getElement().getClassList().add("full-width");
        classeAdd.getElement().getClassList().add("full-width");
        //classeAdd.setItems(Classe);
        classeAdd.setLabel("Classe");

        formAdd.addFormItem(nomeAdd, "Nome");
        formAdd.addFormItem(cognomeAdd, "Cognome");
        formAdd.addFormItem(codiceFiscaleAdd, "Codice Fiscale");
        formAdd.addFormItem(emailAdd, "Email");
        formAdd.addFormItem(dataNascitaAdd, "Data di nascita");
        formAdd.addFormItem(sessoAdd, "Sesso");
        formAdd.addFormItem(numeroTelefonoAdd,"Telefono");
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
            addStudente();
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
                .bind(Studente::getNome, Studente::setNome);
        binderEdit.forField(cognomeEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il cognome", 1, null))
                .bind(Studente::getCognome, Studente::setCognome);
        binderEdit.forField(codiceFiscaleEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il codice fiscale", 16, 16))
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
                .bind(Studente::getCodiceFiscale, Studente::setCodiceFiscale);
        binderEdit.forField(emailEdit)
                .withValidator(new EmailValidator(
                        "Inserire una e-mail valida"))
                .bind(Studente::getUsername, Studente::setUsername);
        binderEdit.forField(dataNascitaEdit)
                .asRequired("Selezionare la data di nascita")
                .bind(Studente::getData, Studente::setData);
        binderEdit.forField(sessoEdit)
                .asRequired("Selezionare il sesso")
                .bind(Studente::getSesso, Studente::setSesso);
        binderEdit.forField(numeroTelefonoEdit)
                .withValidator(new StringLengthValidator(
                        "Inserire il numero di telefono", 1, null))
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
                .bind(Studente::getTelefono, Studente::setTelefono);

        binderEdit.addStatusChangeListener(e -> aggiorna.setEnabled(binderEdit.isValid()));
    }

    private void createAddBinder() {
        binderAdd.forField(nomeAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire il nome", 1, null))
                .bind(Studente::getNome, Studente::setNome);
        binderAdd.forField(cognomeAdd)
                .withValidator(new StringLengthValidator(
                        "Inserire il cognome", 1, null))
                .bind(Studente::getCognome, Studente::setCognome);
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
                .bind(Studente::getCodiceFiscale, Studente::setCodiceFiscale);
        binderAdd.forField(emailAdd)
                .withValidator(new EmailValidator(
                        "Inserire una e-mail valida"))
                .bind(Studente::getUsername, Studente::setUsername);
        binderAdd.forField(dataNascitaAdd)
                .asRequired("Selezionare la data di nascita")
                .bind(Studente::getData, Studente::setData);
        binderAdd.forField(sessoAdd)
                .asRequired("Selezionare il sesso")
                .bind(Studente::getSesso, Studente::setSesso);
        binderAdd.forField(numeroTelefonoAdd)
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
                .bind(Studente::getTelefono, Studente::setTelefono);

        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));
    }

    private void populateForm(Studente value) {
        // Value can be null as well, that clears the form
        binderEdit.readBean(value);
    }

    private void initGrid(){
        studenti = studenteService.findAll();
        grid.setItems(studenti);
    }

    private void updateGrid() {
        //grid.setPageSize(2);
        grid.setItems(studenti);
    }

    private void addStudente() {
        Studente studente = new Studente(nomeAdd.getValue(), cognomeAdd.getValue(), dataNascitaAdd.getValue(),
                codiceFiscaleAdd.getValue(), sessoAdd.getValue(), emailAdd.getValue(), numeroTelefonoAdd.getValue());
        studenteService.createStudente(studente);
        studenti.add(studente);
        Notification.show("Studente aggiunto con successo!");
    }

    private void deleteStudente(){
        Studente studente = grid.getSelectedItems().iterator().next();
        studenteService.deleteById(studente.getId());
        studenti.remove(studente);
        Notification.show("Studente eliminato con successo!");
    }

    private void updateStudente() {
        Studente studenteUpdated = new Studente(nomeEdit.getValue(), cognomeEdit.getValue(), dataNascitaEdit.getValue(),
                codiceFiscaleEdit.getValue(), sessoEdit.getValue(), emailEdit.getValue(), numeroTelefonoEdit.getValue());
        Studente studente = grid.getSelectedItems().iterator().next();
        studenteService.updateStudente(studente, studenteUpdated);
        // studenti.remove(studente);
        // studenti.add(studenteUpdated);
        Notification.show("Studente aggiornato con successo!");
    }

}

//TODO: implementare callback per ACK operazioni su DB?