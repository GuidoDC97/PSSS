package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.ClasseService;
import com.psss.registro.backend.services.StudenteService;
import com.psss.registro.ui.segretario.components.studenti.StudenteGrid;
import com.psss.registro.ui.segretario.components.studenti.StudenteEditor;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.time.Year;

@Route(value = "segretario/studenti", layout = MainView.class)
@PageTitle("Studenti")
@CssImport("./styles/views/studenti/studenti-view.css")
public class StudentiView extends Div {

   /*
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
    private final ComboBox<Classe> classeEdit = new ComboBox<>();

    private final TextField nomeAdd = new TextField();
    private final TextField cognomeAdd = new TextField();
    private final TextField codiceFiscaleAdd = new TextField();
    private final EmailField emailAdd = new EmailField();
    private final DatePicker dataNascitaAdd = new DatePicker();
    private final ComboBox<Character> sessoAdd = new ComboBox<>();
    private final TextField numeroTelefonoAdd = new TextField();
    private final ComboBox<Classe> classeAdd = new ComboBox<>();

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");
    private final Button conferma = new Button("Conferma");

    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");
    private final Button confermaDel = new Button("Conferma");
    private final Button chiudiDel = new Button("Chiudi");

    private final Binder<Studente> binderEdit = new Binder<>(Studente.class);
    private final Binder<Studente> binderAdd = new Binder<>(Studente.class);
*/
    private StudenteService studenteService;
    private ClasseService classeService;

   // private List<Studente> studenti;
    // private List<Classe> classi;

    public StudentiView(StudenteService studenteService, ClasseService classeService) {
        this.classeService = classeService;
        this.studenteService = studenteService;


        //classi = this.classeService.findByAnnoScolastico(Year.now().getValue());
        //studenti = this.studenteService.findAll();

        setId("studenti-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        StudenteGrid studenteGrid = new StudenteGrid(this.studenteService,this.classeService);
        StudenteEditor studenteEditor = new StudenteEditor(this.studenteService,this.classeService);
        studenteEditor.setVisible(false);

        studenteGrid.setEditor(studenteEditor);
        studenteEditor.setGrid(studenteGrid);

        splitLayout.addToPrimary(studenteGrid);
        splitLayout.addToSecondary(studenteEditor);
        add(splitLayout);
    }
}
        /*
        createGridLayout(splitLayout);      // primary: grid
        createEditorLayout(splitLayout);    // secondary: editor

        splitLayout.getSecondaryComponent().setVisible(false);
        add(splitLayout);

        createAddDialog();
        createEditBinder();
        createAddBinder();
        */

/*
    private void createGridLayout(SplitLayout splitLayout) {
        grid.setColumns("nome", "cognome", "codiceFiscale", "sesso", "data", "username", "telefono");
        grid.getColumnByKey("username").setHeader("E-mail");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(studenti);
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
*/
/*
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
        createForm(nomeEdit, cognomeEdit, codiceFiscaleEdit, emailEdit, dataNascitaEdit, sessoEdit,
                numeroTelefonoEdit, classeEdit, formEdit);

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
/*
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
        nomeAdd.setAutofocus(true);

        createForm(nomeAdd, cognomeAdd, codiceFiscaleAdd, emailAdd, dataNascitaAdd, sessoAdd,
                numeroTelefonoAdd, classeAdd, formAdd);

        formAdd.setSizeFull();
        addDiv.add(formAdd);
    }

    private void createForm(TextField nome, TextField cognome, TextField codiceFiscale, EmailField email,
                            DatePicker dataNascita, ComboBox<Character> sesso, TextField numeroTelefono,
                            ComboBox<Classe> classe, FormLayout form) {
        nome.setClearButtonVisible(true);
        nome.getElement().getClassList().add("full-width");

        cognome.setClearButtonVisible(true);
        cognome.getElement().getClassList().add("full-width");

        codiceFiscale.setClearButtonVisible(true);
        codiceFiscale.addValueChangeListener(e->{
            codiceFiscale.setValue(codiceFiscale.getValue().toUpperCase());
        });
        codiceFiscale.getElement().getClassList().add("full-width");

        email.setClearButtonVisible(true);
        email.getElement().getClassList().add("full-width");

        dataNascita.getElement().getClassList().add("full-width");

        sesso.getElement().getClassList().add("full-width");
        sesso.setItems('M','F');

        numeroTelefono.setClearButtonVisible(true);
        numeroTelefono.getElement().getClassList().add("full-width");

        classe.setItems(classi);
        classe.setItemLabelGenerator(Classe::getClasse);
        classe.getElement().getClassList().add("full-width");

        form.addFormItem(nome, "Nome");
        form.addFormItem(cognome, "Cognome");
        form.addFormItem(codiceFiscale, "Codice Fiscale");
        form.addFormItem(email, "Email");
        form.addFormItem(dataNascita, "Data di nascita");
        form.addFormItem(sesso, "Sesso");
        form.addFormItem(numeroTelefono,"Telefono");
        form.addFormItem(classe, "Classe corrente");
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
        createBinder(binderEdit, nomeEdit, cognomeEdit, codiceFiscaleEdit, emailEdit, dataNascitaEdit,
                sessoEdit, numeroTelefonoEdit, classeEdit);

        binderEdit.addStatusChangeListener(e -> aggiorna.setEnabled(binderEdit.isValid()));
    }

    private void createAddBinder() {
        createBinder(binderAdd, nomeAdd, cognomeAdd, codiceFiscaleAdd, emailAdd, dataNascitaAdd,
                sessoAdd, numeroTelefonoAdd, classeAdd);

        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));
    }

    private void createBinder(Binder<Studente> binder, TextField nome, TextField cognome, TextField codiceFiscale,
                              EmailField email, DatePicker dataNascita, ComboBox<Character> sesso,
                              TextField numeroTelefono, ComboBox<Classe> classe) {

        binder.forField(nome)
                .withValidator(new StringLengthValidator(
                        "Inserire il nome", 1, null))
                .bind(Studente::getNome, Studente::setNome);
        binder.forField(cognome)
                .withValidator(new StringLengthValidator(
                        "Inserire il cognome", 1, null))
                .bind(Studente::getCognome, Studente::setCognome);
        binder.forField(codiceFiscale)
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
        binder.forField(email)
                .withValidator(new EmailValidator(
                        "Inserire una e-mail valida"))
                .bind(Studente::getUsername, Studente::setUsername);
        binder.forField(dataNascita)
                .asRequired("Selezionare la data di nascita")
                .bind(Studente::getData, Studente::setData);
        binder.forField(sesso)
                .asRequired("Selezionare il sesso")
                .bind(Studente::getSesso, Studente::setSesso);
        binder.forField(numeroTelefono)
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
        binder.forField(classe)
                .bind(Studente::getClasse, Studente::setClasse);
    }

    private void populateForm(Studente value) {
        // Value can be null as well, that clears the form
        binderEdit.readBean(value);
    }

    private void updateGrid() {
        //grid.setPageSize(2);
        grid.setItems(studenti);
    }


    //TODO: bugga un po il tasto conferma, non sempre si abilita nel modo corretto
    private void addStudente() {
//        Studente studente = studenteService.createStudente(emailAdd.getValue(), nomeAdd.getValue(),
//                cognomeAdd.getValue(), codiceFiscaleAdd.getValue(), sessoAdd.getValue(), dataNascitaAdd.getValue(),
//                numeroTelefonoAdd.getValue(),classeAdd.getValue());
//        studenti.add(studente);
        Notification.show("Studente aggiunto con successo!");
    }

    private void deleteStudente(){
        Studente studente = grid.getSelectedItems().iterator().next();
        studenteService.deleteById(studente.getId());
        studenti.remove(studente);
        Notification.show("Studente eliminato con successo!");
    }

    private void updateStudente() {
        Studente studente = grid.getSelectedItems().iterator().next();
//        studenteService.updateStudente(studente, emailEdit.getValue(), nomeEdit.getValue(), cognomeEdit.getValue(),
//                codiceFiscaleEdit.getValue(), sessoEdit.getValue(), dataNascitaEdit.getValue(),
//                numeroTelefonoEdit.getValue(), classeEdit.getValue());
        Notification.show("Studente aggiornato con successo!");
    }
}

//TODO: implementare callback per ACK operazioni su DB?

*/