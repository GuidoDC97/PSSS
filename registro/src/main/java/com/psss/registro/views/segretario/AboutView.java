package com.psss.registro.views.segretario;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "segretario/about", layout = SegretarioMainView.class)
@RouteAlias(value = "", layout = SegretarioMainView.class)
@PageTitle("About")
@CssImport("./styles/views/about/about-view.css")
public class AboutView extends Div {

    public AboutView() {
        setId("about-view");
        Label titolo = new Label("Registro elettronico segretario");
        titolo.setClassName("text-layout");
        add(titolo);
    }

}
