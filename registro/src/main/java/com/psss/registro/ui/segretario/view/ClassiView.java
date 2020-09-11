package com.psss.registro.ui.segretario.view;

import com.psss.registro.backend.services.ClasseService;
import com.psss.registro.backend.services.DocenteService;
import com.psss.registro.backend.services.InsegnamentoService;
import com.psss.registro.backend.services.StudenteService;
import com.psss.registro.ui.segretario.components.classi.ClasseEditor;
import com.psss.registro.ui.segretario.components.classi.ClasseGrid;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

//TODO: definire final i campi inizializzati fuori dal costruttore.
@Route(value = "segretario/classi", layout = MainView.class)
@PageTitle("Classi")
@CssImport("./styles/views/classi/classi-view.css")
public class ClassiView extends Div {

//    public class AnnoValidator implements Validator {
//
//        @Override
//        public ValidationResult apply(Object o, ValueContext valueContext) {
//            return null;
//        }
//
//        @Override
//        public Object apply(Object o, Object o2) {
//            return null;
//        }
//    }
//
//    private final Details docentiDetails = new Details();
////    private final MultiSelectListBox<String> insegnamentiList = new MultiSelectListBox<>();
//    private final MultiselectComboBox<Insegnamento> insegnamentiCombo = new MultiselectComboBox<>();
//
//    private final Details studentiDetails = new Details();
//    private final ListBox<String> studentiList = new ListBox<>();
//
//    private final Grid<Classe> grid = new Grid<>(Classe.class);
//
//    private final FormLayout formEdit = new FormLayout();
//    private final FormLayout formAdd = new FormLayout();
//    private final FormLayout formInsegnamento = new FormLayout();
//
//    private final Dialog dialogAdd = new Dialog();
//    private final Dialog dialogDel = new Dialog();
//    private final Dialog dialogInsegnamento = new Dialog();
//
//    private final ComboBox<Integer> annoEdit = new ComboBox<Integer> ();
//    private final ComboBox<Character>  sezioneEdit = new ComboBox<Character> ();
//    private final IntegerField annoScolasticoEdit = new IntegerField();
//
//    private final ComboBox<Integer>  annoAdd = new ComboBox<Integer> ();
//    private final ComboBox<Character>  sezioneAdd = new ComboBox<Character> ();
//    private final IntegerField annoScolasticoAdd = new IntegerField();
//
//    private final ComboBox<Docente> docenteInsegnamento = new ComboBox<> ();
//    private final ComboBox<Materia>  materiaInsegnamento = new ComboBox<> ();
//
//    private final TextField filtro = new TextField();
//
//    private final Button aggiungi = new Button("Aggiungi");
//    private final Button conferma = new Button("Conferma");
//
//    private final Button aggiorna = new Button("Aggiorna");
//    private final Button elimina = new Button("Elimina");
//    private final Button confermaDel = new Button("Conferma");
//    private final Button chiudiDel = new Button("Chiudi");
//
//    private final Button confermaInsegnamento = new Button("Conferma");
//
//    private final Binder<Classe> binderEdit = new Binder<>(Classe.class);
//    private final Binder<Classe> binderAdd = new Binder<>(Classe.class);
//    private final Binder<Insegnamento> binderInsegnamento = new Binder<>(Insegnamento.class);

    private ClasseService classeService;
    private DocenteService docenteService;
    private InsegnamentoService insegnamentoService;
    private StudenteService studenteService;

//    private List<Classe> classi;
//    private List<Docente> docenti;

    public ClassiView(ClasseService classeService, DocenteService docenteService, InsegnamentoService insegnamentoService, StudenteService studenteService) {

        this.classeService = classeService;
        this.docenteService = docenteService;
        this.insegnamentoService = insegnamentoService;
        this.studenteService = studenteService;

        setId("classi-view");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        ClasseGrid classeGrid = new ClasseGrid(classeService, insegnamentoService, docenteService);
        ClasseEditor classeEditor = new ClasseEditor(classeService);
        classeEditor.setVisible(false);

        classeGrid.setEditor(classeEditor);
        classeEditor.setGrid(classeGrid);

        splitLayout.addToPrimary(classeGrid);
        splitLayout.addToSecondary(classeEditor);
        add(splitLayout);
    }

//    private void createGridLayout(SplitLayout splitLayout) {
//        grid.setColumns("anno", "sezione", "annoScolastico");
//        grid.addComponentColumn(classe -> {
//            Button insegnamento = new Button("Aggiungi insegnamento");
//            insegnamento.addClickListener(buttonClickEvent -> dialogInsegnamento.open());
//            insegnamento.setEnabled(false);
//            grid.addSelectionListener(selectionEvent -> {
//                insegnamento.setEnabled(selectionEvent.getAllSelectedItems().contains(classe));
//            });
//            return insegnamento;
//        }).setKey("Insegnamento").setHeader("");
//        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
//        grid.setHeightFull();
//        grid.setItems(classi);
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            populateForm(event.getValue());
//            splitLayout.getSecondaryComponent().setVisible(!event.getHasValue().isEmpty());
//
//            //docenti details
//            Classe classe = event.getValue();
//            Component editor = splitLayout.getSecondaryComponent();
//
//            //populateForm(classe);
//
//            editor.setVisible(!event.getHasValue().isEmpty());
//
//            docentiDetails.setOpened(false);
////            insegnamentiCombo.setItems();
//
////            if(editor.isVisible()) {
////                List<String> insegnamenti = new LinkedList<>();
////                for (Insegnamento insegnamento : classe.getInsegnamenti()) {
////                    insegnamenti.add(insegnamento.getDocente().getDocente() + "("
////                            + insegnamento.getMateria().getNome() + ")");
////                }
////                insegnamentiCombo.setItems(classe.getInsegnamenti());
//////
////                docentiDetails.setContent(insegnamentiCombo);
////            }
//
//            studentiDetails.setOpened(false);
//            studentiList.setItems("");
//
//
//            if(editor.isVisible()) {
//                //TODO: Nota: con questa strategia ogni selezione della classe implica il caricamento degli studenti ad
//                // essa associati dal database
//                //Classe classeSync = classeService.findById(classe.getId()).get();
//                List<String> studentiLabel = new LinkedList<>();
//                List<Studente> studenti = studenteService.findByClasse(classe);
//                for(Studente studente : studenti){
//                    studentiLabel.add(studente.getNome() + " " + studente.getCognome());
//                }
////                for (Studente studente : classe.getStudenti()) {
////                    studentiLabel.add(studente.getNome() + " " + studente.getCognome());
////                }
//                studentiList.setItems(studentiLabel);
//                studentiDetails.setContent(studentiList);
//            }
//
//        });
//
//        Div wrapper = new Div();
//        wrapper.setId("grid-wrapper");
//        wrapper.setWidthFull();
//
//        createToolbarLayout(wrapper);
//        createInsegnamentoDialog();
//
//        wrapper.add(grid);
//
//        splitLayout.addToPrimary(wrapper);
//    }
//
//    private void createToolbarLayout(Div wrapper) {
//        HorizontalLayout toolBarLayout = new HorizontalLayout();
//        toolBarLayout.setId("button-layout");
//        toolBarLayout.setWidthFull();
//        toolBarLayout.setSpacing(false);
//        toolBarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
//
//        filtro.setPlaceholder("Cerca...");
//        filtro.setClearButtonVisible(true);
//        filtro.setValueChangeMode(ValueChangeMode.LAZY);
//        filtro.addValueChangeListener(event -> {
//            Set<Classe> foundClassi = classi.stream()
//                    .filter(classe -> classe.getAnno() == Integer.parseInt(event.getValue()) ||
//                    classe.getSezione() == Integer.parseInt(event.getValue().toLowerCase()))
//                    .collect(Collectors.toSet());
//            grid.setItems(foundClassi);
//        });
//
//        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        aggiungi.addClickListener(event -> dialogAdd.open());
//
//        toolBarLayout.add(filtro, aggiungi);
//
//        wrapper.add(toolBarLayout);
//    }
//
//    private void createInsegnamentoDialog() {
//        dialogInsegnamento.setId("editor-layout");
//
//        Label titolo = new Label("Nuova Insegnamento");
//        titolo.setClassName("bold-text-layout");
//        dialogInsegnamento.add(titolo);
//        Div insegnamentoDiv = new Div();
//        insegnamentoDiv.setId("editor");
//        dialogInsegnamento.add(insegnamentoDiv);
//
//        createFormInsegnamentoLayout(insegnamentoDiv);
//        createButtonInsegnamentoLayout(dialogInsegnamento, insegnamentoDiv);
//
//        dialogInsegnamento.setCloseOnEsc(true);
//        dialogInsegnamento.addOpenedChangeListener(e -> {
//            if(!e.isOpened()) {
//                binderInsegnamento.readBean(null);
//            }
//        });
//    }
//
//    private void createButtonInsegnamentoLayout(Dialog dialogInsegnamento, Div insegnamentoDiv) {
//        HorizontalLayout confermaLayout = new HorizontalLayout();
//        confermaLayout.setId("button-layout");
//        confermaLayout.setWidthFull();
//        confermaLayout.setSpacing(true);
//        confermaInsegnamento.setEnabled(false);
//        confermaInsegnamento.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        confermaInsegnamento.addClickShortcut(Key.ENTER).listenOn(insegnamentoDiv);
//        confermaInsegnamento.addClickListener(e -> {
//            addInsegnamento();
//            updateGrid();
//            dialogInsegnamento.close();
//        });
//
//        confermaLayout.add(confermaInsegnamento);
//        dialogInsegnamento.add(confermaLayout);
//    }
//
//    private void createFormInsegnamentoLayout(Div insegnamentoDiv) {
//        getElement().getClassList().add("full-width");
//        docenteInsegnamento.setItems(docenti);
//        docenteInsegnamento.setItemLabelGenerator(Docente::getDocente);
//        docenteInsegnamento.addValueChangeListener(event -> {
//            if(event.getValue()!= null) {
//                materiaInsegnamento.setEnabled(true);
//                materiaInsegnamento.setItems(event.getValue().getMaterie());
//            }
//        });
//
//        materiaInsegnamento.getElement().getClassList().add("full-width");
//        materiaInsegnamento.setEnabled(false);
//        materiaInsegnamento.setItemLabelGenerator(Materia::getNome);
////        materiaInsegnamento.addValueChangeListener(event -> docenteInsegnamento.setItems(event.getValue().getDocenti()));
//
//        formInsegnamento.addFormItem(docenteInsegnamento, "Docente");
//        formInsegnamento.addFormItem(materiaInsegnamento, "Materia");
//        formInsegnamento.setSizeFull();
//        insegnamentoDiv.add(formInsegnamento);
//    }
//
//    private void createEditorLayout(SplitLayout splitLayout) {
//        Div editorLayoutDiv = new Div();
//        editorLayoutDiv.setId("editor-layout");
//
//        Div editorDiv = new Div();
//        editorDiv.setId("editor");
//
//        Label titolo = new Label("Scheda classe");
//        titolo.setClassName("bold-text-layout");
//        editorDiv.add(titolo);
//
//        editorLayoutDiv.add(editorDiv);
//
//        createFormEditLayout(editorDiv);
//        createStudentiDetails(editorDiv);
//        createDocentiDetails(editorDiv);
//
//        createButtonEditLayout(editorLayoutDiv);
//
//        splitLayout.addToSecondary(editorLayoutDiv);
//    }
//
//    private void createDocentiDetails(Div editorLayoutDiv) {
//        docentiDetails.setSummaryText("Docenti");
//        docentiDetails.setContent(insegnamentiCombo);
//
//        insegnamentiCombo.setItemLabelGenerator(Insegnamento::getDocenteMateria);
//        insegnamentiCombo.setCompactMode(false);
////        insegnamentiCombo.setReadOnly(true);
//
//        editorLayoutDiv.add(docentiDetails);
//    }
//
//    private void createStudentiDetails(Div editorLayoutDiv) {
//        studentiDetails.setSummaryText("Studenti");
//        studentiList.setReadOnly(true);
//
//        editorLayoutDiv.add(studentiDetails);
//    }
//
//    private void createFormEditLayout(Div editorDiv) {
//
//        annoEdit.getElement().getClassList().add("full-width");
//        annoEdit.setItems(1,2,3,4,5);
//        sezioneEdit.getElement().getClassList().add("full-width");
//        sezioneEdit.setItems('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
//        annoScolasticoEdit.setClearButtonVisible(true);
//        annoScolasticoEdit.getElement().getClassList().add("full-width");
//        formEdit.addFormItem(annoEdit, "Anno");
//        formEdit.addFormItem(sezioneEdit, "Sezione");
//        formEdit.addFormItem(annoScolasticoEdit,"Anno scolastico");
//        editorDiv.add(formEdit);
//
//        createDeleteDialog();
//    }
//
//    private void createDeleteDialog() {
//        dialogDel.setCloseOnEsc(false);
//        dialogDel.setCloseOnOutsideClick(false);
//
//        Div delDiv = new Div();
//        delDiv.setId("editor");
//
//        Label text = new Label("Sei sicuro di voler eliminare una classe?");
//        text.setClassName("text-layout");
//        delDiv.add(text);
//
//        dialogDel.add(delDiv);
//
//        createButtonDelLayout(dialogDel);
//    }
//
//    private void createButtonDelLayout(Dialog dialogDel) {
//        HorizontalLayout confermaLayout = new HorizontalLayout();
//        confermaLayout.setId("button-layout");
//        confermaLayout.setWidthFull();
//        confermaLayout.setSpacing(false);
//        confermaLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
//
//        confermaDel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        confermaDel.addClickListener(e -> {
//            deleteClasse();
//            updateGrid();
//            grid.asSingleSelect().clear();
//            dialogDel.close();
//        });
//        chiudiDel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
//        chiudiDel.addClickListener(e -> {
//            dialogDel.close();
//        });
//
//        confermaLayout.add(confermaDel, chiudiDel);
//        dialogDel.add(confermaLayout);
//    }
//
//    private void createButtonEditLayout(Div editorLayoutDiv) {
//        HorizontalLayout buttonLayout = new HorizontalLayout();
//        buttonLayout.setId("button-layout");
//        buttonLayout.setWidthFull();
//        buttonLayout.setSpacing(false);
//        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
//
//        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR);
//        elimina.addClickListener(event -> dialogDel.open());
//
//        aggiorna.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        aggiorna.addClickShortcut(Key.ENTER).listenOn(editorLayoutDiv);
//        aggiorna.addClickListener(e -> {
//            updateClasse();
//            updateGrid();
//            grid.asSingleSelect().clear();
//        });
//
//        buttonLayout.add(aggiorna, elimina);
//        editorLayoutDiv.add(buttonLayout);
//    }
//
//    private void createAddDialog() {
//        dialogAdd.setId("editor-layout");
//
//        Label titolo = new Label("Nuova classe");
//        titolo.setClassName("bold-text-layout");
//        dialogAdd.add(titolo);
//        Div addDiv = new Div();
//        addDiv.setId("editor");
//        dialogAdd.add(addDiv);
//
//        createFormAddLayout(addDiv);
//        createButtonAddLayout(dialogAdd, addDiv);
//
//        dialogAdd.setCloseOnEsc(true);
//        dialogAdd.addOpenedChangeListener(e -> {
//            if(!e.isOpened()) {
//                binderAdd.readBean(null);
//            }
//        });
//    }
//
//    private void createFormAddLayout(Div addDiv) {
//        annoAdd.setAutofocus(true);
//        annoAdd.getElement().getClassList().add("full-width");
//        annoAdd.setItems(1,2,3,4,5);
//        sezioneAdd.getElement().getClassList().add("full-width");
//        sezioneAdd.setItems('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
//        annoScolasticoAdd.setClearButtonVisible(true);
//        annoScolasticoAdd.getElement().getClassList().add("full-width");
//        formAdd.addFormItem(annoAdd, "Anno");
//        formAdd.addFormItem(sezioneAdd, "Sezione");
//        formAdd.addFormItem(annoScolasticoAdd, "Anno scolastico");
//        formAdd.setSizeFull();
//        addDiv.add(formAdd);
//    }
//
//    private void createButtonAddLayout(Dialog dialogAdd, Div addDiv) {
//        HorizontalLayout confermaLayout = new HorizontalLayout();
//        confermaLayout.setId("button-layout");
//        confermaLayout.setWidthFull();
//        confermaLayout.setSpacing(true);
//        conferma.setEnabled(false);
//        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        conferma.addClickShortcut(Key.ENTER).listenOn(addDiv);
//        conferma.addClickListener(e -> {
//            addClasse();
//            updateGrid();
//            dialogAdd.close();
//        });
//
//        confermaLayout.add(conferma);
//        dialogAdd.add(confermaLayout);
//    }
//
//    private void createEditBinder() {
//
//        binderEdit.forField(annoEdit).asRequired("Selezionare l'anno").bind(Classe::getAnno, Classe::setAnno);
//        binderEdit.forField(sezioneEdit).asRequired("Selezionare la sezione").bind(Classe::getSezione, Classe::setSezione);
//        binderEdit.forField(annoScolasticoEdit).asRequired("Inserire l'anno scolastico")
//                .bind(Classe::getAnnoScolastico, Classe::setAnnoScolastico);
//        binderEdit.forField(insegnamentiCombo).bind(Classe::getInsegnamenti, Classe::setInsegnamenti);
//
//        binderEdit.addStatusChangeListener(e -> aggiorna.setEnabled(binderEdit.isValid()));
//    }
//
//    private void createAddBinder() {
//        binderAdd.forField(annoAdd).asRequired("Selezionare l'anno").bind(Classe::getAnno, Classe::setAnno);
//        binderAdd.forField(sezioneAdd).asRequired("Selezionare la sezione").bind(Classe::getSezione, Classe::setSezione);
//        binderAdd.forField(annoScolasticoAdd).asRequired("Inserire l'anno scolastico")
//                .bind(Classe::getAnnoScolastico, Classe::setAnnoScolastico);
//
//        binderAdd.addStatusChangeListener(e -> conferma.setEnabled(binderAdd.isValid()));
//    }
//
//    private void createInsegnamentoBinder() {
//        binderInsegnamento.forField(docenteInsegnamento)
//                .asRequired("Selezionare il docente").bind(Insegnamento::getDocente, Insegnamento::setDocente);
//        binderInsegnamento.forField(materiaInsegnamento)
//                .asRequired("Selezionare la materia").bind(Insegnamento::getMateria, Insegnamento::setMateria);
//
//        binderInsegnamento.addStatusChangeListener(e -> confermaInsegnamento.setEnabled(binderInsegnamento.isValid()));
//    }
//
//    private void populateForm(Classe value) {
//        binderEdit.readBean(value);
//    }
//
//    private void updateGrid() {
//        grid.setItems(classi);
//    }
//
//    //TODO: gestire correttamente le liste che costituiscono la griglia come ha fatto Fabio.
//    //TODO: gestire bene il costruttore
//    private void addClasse() {
////        Classe classe = classeService.createClasse(annoAdd.getValue(), sezioneAdd.getValue(), annoScolasticoAdd.getValue());
////        classi.add(classe);
//        Notification.show("Classe aggiunta con successo!");
//    }
//
//    private void deleteClasse(){
//        Classe classe = grid.getSelectedItems().iterator().next();
//        classeService.deleteById(classe.getId());
//        classi.remove(classe);
//        Notification.show("Classe eliminata con successo!");
//    }
//
//    private void updateClasse() {
//        boolean exist = false;
////        System.out.println(classeUpdated);
//        for(Classe classeInList : classi){
//            //System.out.println(classeInList.equals(classeUpdated));
////            System.out.println(classeInList);
////
////            if(classeInList.equals(classeUpdated)) {
////                Notification.show("Materia già esistente!");
////                exist = true;
////                break;
////            }
//        }
//        if(true){
//            Classe classe = grid.getSelectedItems().iterator().next();
////            classeService.updateClasse(classe, annoEdit.getValue(), sezioneEdit.getValue(),
////                    annoScolasticoEdit.getValue(), insegnamentiCombo.getSelectedItems());
//            Notification.show("Classe aggiornata con successo!");
//        }
//    }
//
//    private void addInsegnamento() {
////        insegnamentoService.createInsegnamento(docenteInsegnamento.getValue(), materiaInsegnamento.getValue(),
////                grid.getSelectedItems().iterator().next());
//        Notification.show("Insegnamento inserito con successo!");
//    }
}