package com.psss.registro.ui.segretario.components.classi;


import com.psss.registro.backend.models.Insegnamento;
import com.psss.registro.backend.services.DocenteService;
import com.psss.registro.backend.services.InsegnamentoService;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class InsegnamentoDialog extends Dialog {

    private final InsegnamentoForm form;

    private final Button conferma = new Button("Conferma");

//    private ClasseGrid grid;

    private InsegnamentoService insegnamentoService;
    private DocenteService docenteService;

    public InsegnamentoDialog(InsegnamentoService insegnamentoService, DocenteService docenteService) {
        setId("editor-layout");

        this.insegnamentoService = insegnamentoService;
        this.docenteService = docenteService;

        form = new InsegnamentoForm(this.docenteService);

        Label titolo = new Label("Nuovo insegnamento");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        form.getDocente().setAutofocus(true);

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
            Insegnamento insegnamento = new Insegnamento();
//            Classe classe = grid.getGrid().getSelectedItems().iterator().next();
            form.getBinder().writeBeanIfValid(insegnamento);
            insegnamentoService.saveInsegnamento(insegnamento);
            Notification.show("Insegnamento aggiunto con successo!");
            System.out.println("Insegnamento aggiunto: " + insegnamento.toString());
            close();
        });

        buttonLayout.add(conferma);

        return buttonLayout;
    }

//    public void setGrid(ClasseGrid grid) {
//        this.grid = grid;
//    }

    public InsegnamentoForm getForm() {
        return form;
    }
}
