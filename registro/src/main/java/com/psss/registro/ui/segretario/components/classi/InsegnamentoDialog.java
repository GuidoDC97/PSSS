package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.models.Insegnamento;
import com.psss.registro.backend.services.ServiceFacade;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

class InsegnamentoDialog extends Dialog {

    private final InsegnamentoForm form;

    private final Button conferma = new Button("Conferma");

    private ServiceFacade serviceFacade;

    public InsegnamentoDialog(ServiceFacade serviceFacade) {
        setId("editor-layout");

        this.serviceFacade = serviceFacade;

        form = new InsegnamentoForm(this.serviceFacade);

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
            form.getBinder().writeBeanIfValid(insegnamento);
            Notification notification = new Notification();
            notification.setDuration(3000);
            if(serviceFacade.saveInsegnamento(insegnamento)) {
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.setText("Insegnamento inserito con successo!");
                notification.open();
                close();
            } else {
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.setText("Attenzione: non è possibile inserire l'insegmaneto!");
                notification.open();
            }
        });

        form.getBinder().addStatusChangeListener(e -> conferma.setEnabled(form.getBinder().isValid()));

        buttonLayout.add(conferma);

        return buttonLayout;
    }

    public InsegnamentoForm getForm() {
        return form;
    }
}
