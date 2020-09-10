package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Insegnamento;
import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.ClasseService;
import com.psss.registro.backend.services.DocenteService;
import com.psss.registro.backend.services.InsegnamentoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClasseGrid extends Div {

    private final Grid<Classe> grid = new Grid<>(Classe.class);

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");

    private ClasseDialog classeDialog;

    private ClasseEditor editor;

    private ClasseService classeService;
    private InsegnamentoService insegnamentoService;
    private DocenteService docenteService;

    private List<Classe> classi;

    public ClasseGrid(ClasseService classeService, InsegnamentoService insegnamentoService, DocenteService docenteService) {
        setId("grid-wrapper");
        setWidthFull();

        this.classeService = classeService;
        this.insegnamentoService = insegnamentoService;
        this.docenteService = docenteService;

        classi = this.classeService.findAll();

        grid.setColumns("anno", "sezione", "annoScolastico");
        grid.addComponentColumn(classe -> {

            Button insegnamentoButton = new Button("Aggiungi insegnamento");

            insegnamentoButton.addClickListener(buttonClickEvent -> {
                InsegnamentoDialog insegnamentoDialog = new InsegnamentoDialog(insegnamentoService, docenteService);
                insegnamentoDialog.setCloseOnEsc(true);

                Classe classeSelezionata = grid.getSelectedItems().iterator().next();
                insegnamentoDialog.getForm().getClasse().setItems(classeSelezionata);
                insegnamentoDialog.getForm().getClasse().setValue(classeSelezionata);

                insegnamentoDialog.open();

                insegnamentoDialog.addOpenedChangeListener(e -> {
                    if(!e.isOpened()) {
                        insegnamentoDialog.getForm().getBinder().readBean(null);
                    }
                });
            });
            insegnamentoButton.setEnabled(false);

            grid.addSelectionListener(selectionEvent -> {
                insegnamentoButton.setEnabled(selectionEvent.getAllSelectedItems().contains(classe));
            });

            return insegnamentoButton;
        }).setKey("Insegnamento").setHeader("");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(classi);
        grid.asSingleSelect().addValueChangeListener(event -> {
            Classe classe = event.getValue();

            editor.getForm().getBinder().readBean(classe);
            editor.setVisible(!event.getHasValue().isEmpty());

            editor.getStudentiDetails().setOpened(false);
            editor.getStudentiList().setItems((Studente) null);

            if(editor.isVisible()) {
                editor.getStudentiList().setItems(classe.getStudenti());
            }

            editor.getInsegnamentiDetails().setOpened(false);
            editor.getInsegnamentiList().setItems((Insegnamento) null);

            if(editor.isVisible()) {
                editor.getInsegnamentiList().setItems(classe.getInsegnamenti());
                System.out.println(classe.getInsegnamenti());
            }
        });

        add(createToolbarLayout(), grid);
    }

    private HorizontalLayout createToolbarLayout() {
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
        aggiungi.addClickListener(event -> {
            classeDialog = new ClasseDialog(this.classeService);
            classeDialog.setGrid(this);
            classeDialog.open();
        });

        toolBarLayout.add(filtro, aggiungi);

        return toolBarLayout;
    }

    public Grid<Classe> getGrid() {
        return grid;
    }

    public List<Classe> getClassi() {
        return classi;
    }

    public void setEditor(ClasseEditor editor) {
        this.editor = editor;
    }

}
