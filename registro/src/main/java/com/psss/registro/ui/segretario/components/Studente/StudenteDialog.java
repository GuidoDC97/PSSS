package com.psss.registro.ui.segretario.components.Studente;


import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.StudenteService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class StudenteDialog extends Dialog{
    private final StudenteForm form = new StudenteForm();

    private final Button conferma = new Button("Conferma");

    private StudenteGrid grid;

    private StudenteService studenteService;

    public StudenteDialog(StudenteService studenteService) {
        setId("editor-layout");

        this.studenteService = studenteService;

        Label titolo = new Label("Nuovo Studente");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        add(formDiv, createButtonLayout(formDiv));

        addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
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
                Studente studente = new Studente();
                form.getBinder().writeBeanIfValid(studente);
             //   studenteService.update(studente);
                Notification.show("Studente aggiornato con successo!");
                System.out.println("Studente aggiunto: " + studente.toString());
                grid.getStudenti().add(studente);
                grid.getGrid().setItems(grid.getStudenti());
                close();
            });

            buttonLayout.add(conferma);

            return buttonLayout;
        }

        public void setGrid(StudenteGrid grid) {
            this.grid = grid;
        }

}
