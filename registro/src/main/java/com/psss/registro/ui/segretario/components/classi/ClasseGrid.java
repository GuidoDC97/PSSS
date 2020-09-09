package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.services.ClasseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
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

    private ClasseDialog dialog;

    private ClasseEditor editor;

    private ClasseService classeService;

    private List<Classe> classi;

    public ClasseGrid(ClasseService classeService) {
        setId("grid-wrapper");
        setWidthFull();

        this.classeService = classeService;
        classi = this.classeService.findAll();


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
            dialog = new ClasseDialog(this.classeService);
            dialog.setGrid(this);
            dialog.open();
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
