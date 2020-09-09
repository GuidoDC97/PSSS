package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.services.ClasseService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ClasseDialog extends Dialog{

    private final ClasseForm form = new ClasseForm();

    private final Button conferma = new Button("Conferma");

    private ClasseGrid grid;

    private ClasseService classeService;

    public ClasseDialog(ClasseService classeService) {
        setId("editor-layout");

        this.classeService = classeService;

        Label titolo = new Label("Nuova classe");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        form.getAnno().setAutofocus(true);

        add(formDiv, createButtonLayout(formDiv));

        addOpenedChangeListener(e -> {
            if(!e.isOpened()) {
                form.getBinder().readBean(null);
            }
        });
    }

    private HorizontalLayout createButtonLayout(Div formDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(false);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        conferma.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        conferma.addClickShortcut(Key.ENTER).listenOn(formDiv);
        conferma.addClickListener(e -> {
            Classe classe = new Classe();
            form.getBinder().writeBeanIfValid(classe);
            classeService.saveClasse(classe);
            Notification.show("Classe aggiornata con successo!");
            System.out.println("Classe aggiunta: " + classe.toString());
            grid.getClassi().add(classe);
            grid.getGrid().setItems(grid.getClassi());
            close();
        });

        buttonLayout.add(conferma);

        return buttonLayout;
    }

    public void setGrid(ClasseGrid grid) {
        this.grid = grid;
    }
}


