package com.psss.registro.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginForm login = new LoginForm();
//    private LoginOverlay login = new LoginOverlay();

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        Image image = new Image("icons/icon.png", "Registro logo");
        image.setWidth("200px");
        image.setHeight("200px");
        add(image, new Text("Registro Elettronico"), login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
        // UI.getCurrent().getPage().executeJavaScript("document.getElementById(\"vaadinLoginUsername\").focus();");
    }
}
