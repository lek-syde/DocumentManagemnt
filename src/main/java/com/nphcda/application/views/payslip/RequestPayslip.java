package com.nphcda.application.views.payslip;

import com.nphcda.application.data.Role;
import com.nphcda.application.data.entity.PaySlipRequest;
import com.nphcda.application.data.entity.User;
import com.nphcda.application.data.service.PayslipRequestRepo;
import com.nphcda.application.data.service.UserRepository;
import com.nphcda.application.security.AuthenticatedUser;
import com.nphcda.application.views.Homelayout;
import com.nphcda.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@PageTitle("Request Payslip | NPHCDA")
@Route(value = "request-payslip", layout = MainLayout.class)
@RolesAllowed("user")
@Uses(Icon.class)
public class RequestPayslip extends Div {


    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();



    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");

    private ComboBox<?> yearPicker = new ComboBox<>("From");
    private ComboBox<?> monthPicker = new ComboBox<>("To");

    private ComboBox<?> yearPicker2 = new ComboBox<>("From");
    private ComboBox<?> monthPicker2 = new ComboBox<>("To");


    private ComboBox<String> dayPicker = new ComboBox<>("To");


    private Button cancel = new Button("Cancel");
    private Button save = new Button("Submit Request");
    FormLayout formLayout = new FormLayout();
    Tabs tabs;
    Tab tab1 = new Tab("Single Month");
    Tab tab2 = new Tab("Multiple Month");

    String selectedtab="single";

    private AuthenticatedUser authenticatedUser;
    private PayslipRequestRepo payslipRequestRepo;

    public RequestPayslip(PayslipRequestRepo payslipRequestRepo, AuthenticatedUser authenticatedUser) {
        this.authenticatedUser=authenticatedUser;
            this.payslipRequestRepo=payslipRequestRepo;


        tab1 = new Tab("Single Month");
        tab2 = new Tab("Multiple Month");
        tabs = new Tabs(true, tab1, tab2);

//        radioGroup.setLabel("Request type");
//        radioGroup.setItems("Single Month", "Multiple Months");
//        radioGroup.setValue("Single Month");



        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())

        );




        addClassName("person-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {


            PaySlipRequest paySlipRequest= new PaySlipRequest();

            System.out.println("tab"+ selectedtab);
            paySlipRequest.setRequestType(selectedtab);
            paySlipRequest.setStartYear(yearPicker.getValue().toString());
            paySlipRequest.setStartMonth(monthPicker.getValue().toString());

            if(selectedtab=="multiple"){

                paySlipRequest.setEndYear(yearPicker2.getValue().toString());
                paySlipRequest.setEndMonth(monthPicker2.getValue().toString());
            }
            paySlipRequest.setUserdetails(authenticatedUser.get().get());
            payslipRequestRepo.save(paySlipRequest);


            Notification.show( "Request Sent.");
            clearForm();
        });
    }

    private void setContent(Tab selectedTab) {

 HorizontalLayout hl1= new HorizontalLayout(yearPicker, monthPicker);
 HorizontalLayout hl2= new HorizontalLayout(yearPicker2, monthPicker2);




        if (selectedTab.equals(tab1)) {
            formLayout.remove(hl2);
            formLayout.remove(hl1);


            formLayout.add( hl1);
            formLayout.remove(hl2);
            selectedtab="single";
        } else {
            formLayout.remove(hl2);
            formLayout.remove(hl1);

            formLayout.add( hl1);
            formLayout.add(hl2);
            selectedtab="multiple";
        }
    }

    private void clearForm() {

    }

    private Component createTitle() {
        return new H3("Request payslip");
    }

    private Component createFormLayout() {

        formLayout.setMaxWidth("500px");
        //formLayout.add(radioGroup, firstName, lastName, From, To);

        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        List<Integer> selectableYears = IntStream.range(
                        now.getYear() - 99,
                        now.getYear() + 1)
                .boxed().collect(Collectors.toList());

        yearPicker = new ComboBox("Year", selectableYears);
        yearPicker.setWidth(6, Unit.EM);
        yearPicker.addValueChangeListener(e -> {
            updateMonthPicker();
        });
        yearPicker.setHelperText("Start year");


        yearPicker2 = new ComboBox("Year", selectableYears);
        yearPicker2.setWidth(6, Unit.EM);
        yearPicker2.addValueChangeListener(e -> {
            updateMonthPicker2();
        });
        yearPicker2.setHelperText("End year");

        monthPicker = new ComboBox("Month", Month.values());


        monthPicker.setWidth(9, Unit.EM);

        monthPicker.setEnabled(false);
        monthPicker.setHelperText("Start month");


        monthPicker2 = new ComboBox("Month", Month.values());


        monthPicker2.setWidth(9, Unit.EM);

        monthPicker2.setEnabled(false);
        monthPicker2.setHelperText("End Month");


        formLayout.add(tabs);

        setContent(tab1);

        return formLayout;
    }

    private void updateMonthPicker2() {
        if (yearPicker2.getValue() == null) {
            monthPicker2.setValue(null);
            monthPicker2.setEnabled(false);
            return;
        }

        monthPicker2.setValue(null);
        monthPicker2.setEnabled(true);
    }


    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);

        buttonLayout.setWidthFull();
        return buttonLayout;
    }




    private void updateMonthPicker() {
        if (yearPicker.getValue() == null) {
            monthPicker.setValue(null);
            monthPicker.setEnabled(false);
            return;
        }

        monthPicker.setValue(null);
        monthPicker.setEnabled(true);
    }




}
