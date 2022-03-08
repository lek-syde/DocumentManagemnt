package com.nphcda.application.views.personform;

import com.nphcda.application.data.Role;
import com.nphcda.application.data.entity.EmployeeDetail;
import com.nphcda.application.data.entity.User;
import com.nphcda.application.data.service.SamplePersonService;
import com.nphcda.application.data.service.UserRepository;
import com.nphcda.application.data.service.UserService;
import com.nphcda.application.views.Homelayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Sign Up")
@Route(value = "signup", layout = Homelayout.class)
@AnonymousAllowed
@Uses(Icon.class)
public class SignupFormView extends Div {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email address");
    private DatePicker dateOfBirth = new DatePicker("Birthday");
    private PhoneNumberField phone = new PhoneNumberField("Phone number");
    private ComboBox<String> department = new ComboBox<>("Department");

    private PasswordField password = new PasswordField("password");
    private PasswordField confirmPassword= new PasswordField("Confirm password");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Register");



    public SignupFormView(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        department.setItems("ED Office", "PRS", "DCI");
        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {

            User newUser = new User();
            newUser.setName(firstName.getValue());
            newUser.setUsername(email.getValue());

            newUser.setFirstName(firstName.getValue());
            newUser.setFirstName(lastName.getValue());
            newUser.setEmail(email.getValue());
            newUser.setPhone(phone.getValue());
            newUser.setDateOfBirth(dateOfBirth.getValue());
            newUser.setHashedPassword(passwordEncoder.encode(password.getValue()));
            newUser.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");


            newUser.setRoles(Stream.of(Role.USER, Role.ADMIN).collect(Collectors.toSet()));



            userRepository.save(newUser);



            Notification.show( " details stored.");
            clearForm();
        });
    }

    private void clearForm() {

    }

    private Component createTitle() {
        return new H3("Personal information");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(firstName, lastName, dateOfBirth, phone, email, department, password, confirmPassword);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }

    private static class PhoneNumberField extends CustomField<String> {
        private ComboBox<String> countryCode = new ComboBox<>();
        private TextField number = new TextField();

        public PhoneNumberField(String label) {
            setLabel(label);
            countryCode.setWidth("120px");
            countryCode.setPlaceholder("Country");
            countryCode.setPattern("\\+\\d*");
            countryCode.setPreventInvalidInput(true);
            countryCode.setItems("+234");
            countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
            number.setPattern("\\d*");
            number.setPreventInvalidInput(true);
            HorizontalLayout layout = new HorizontalLayout(countryCode, number);
            layout.setFlexGrow(1.0, number);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (countryCode.getValue() != null && number.getValue() != null) {
                String s = countryCode.getValue() + " " + number.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
                countryCode.clear();
                number.setValue(parts[0]);
            } else if (parts.length == 2) {
                countryCode.setValue(parts[0]);
                number.setValue(parts[1]);
            } else {
                countryCode.clear();
                number.clear();
            }
        }
    }

}
