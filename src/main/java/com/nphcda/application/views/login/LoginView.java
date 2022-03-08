package com.nphcda.application.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout {

    LoginForm loginForm= new LoginForm();
    public LoginView() {
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(true);


        Anchor SignupLink = new Anchor("signup", "Sign Up");
        add(loginForm, SignupLink);
        setAlignItems(Alignment.CENTER);



    }



}
