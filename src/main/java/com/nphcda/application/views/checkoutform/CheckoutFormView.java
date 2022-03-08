package com.nphcda.application.views.checkoutform;

import com.nphcda.application.views.MainLayout;
import com.nphcda.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@PageTitle("Checkout Form")
@Route(value = "checkout-form", layout = MainLayout.class)
@AnonymousAllowed
public class CheckoutFormView extends Div {

    private static final Set<String> states = new LinkedHashSet<>();
    private static final Set<String> countries = new LinkedHashSet<>();



    public CheckoutFormView() {
        addClassNames("checkout-form-view", "flex", "flex-col", "h-full");

        Main content = new Main();
        content.addClassNames("grid", "gap-xl", "items-start", "justify-center", "max-w-screen-md", "mx-auto", "pb-l",
                "px-l");

        content.add(createCheckoutForm());
        content.add(createAside());
        add(content);
    }

    private Component createCheckoutForm() {
        Section checkoutForm = new Section();
        checkoutForm.addClassNames("flex", "flex-col", "flex-grow");

        H2 header = new H2("Checkout");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        Paragraph note = new Paragraph("All fields are required unless otherwise noted");
        note.addClassNames("mb-xl", "mt-0", "text-secondary");
        checkoutForm.add(header, note);

        checkoutForm.add(createPersonalDetailsSection());
        checkoutForm.add(createShippingAddressSection());
        checkoutForm.add(createPaymentInformationSection());
        checkoutForm.add(new Hr());
        checkoutForm.add(createFooter());

        return checkoutForm;
    }

    private Section createPersonalDetailsSection() {
        Section personalDetails = new Section();
        personalDetails.addClassNames("flex", "flex-col", "mb-xl", "mt-m");

        Paragraph stepOne = new Paragraph("Checkout 1/3");
        stepOne.addClassNames("m-0", "text-s", "text-secondary");

        H3 header = new H3("Personal details");
        header.addClassNames("mb-m", "mt-s", "text-2xl");

        TextField name = new TextField("Name");
        name.setRequiredIndicatorVisible(true);
        name.setPattern("[\\p{L} \\-]+");
        name.addClassNames("mb-s");

        EmailField email = new EmailField("Email address");
        email.setRequiredIndicatorVisible(true);
        email.addClassNames("mb-s");

        TextField phone = new TextField("Phone number");
        phone.setRequiredIndicatorVisible(true);
        phone.setPattern("[\\d \\-\\+]+");
        phone.addClassNames("mb-s");

        Checkbox rememberDetails = new Checkbox("Remember personal details for next time");
        rememberDetails.addClassNames("mt-s");

        personalDetails.add(stepOne, header, name, email, phone, rememberDetails);
        return personalDetails;
    }

    private Section createShippingAddressSection() {
        Section shippingDetails = new Section();
        shippingDetails.addClassNames("flex", "flex-col", "mb-xl", "mt-m");

        Paragraph stepTwo = new Paragraph("Checkout 2/3");
        stepTwo.addClassNames("m-0", "text-s", "text-secondary");

        H3 header = new H3("Shipping address");
        header.addClassNames("mb-m", "mt-s", "text-2xl");

        ComboBox countrySelect = new ComboBox("Country");
        countrySelect.setRequiredIndicatorVisible(true);
        countrySelect.addClassNames("mb-s");

        TextArea address = new TextArea("Street address");
        address.setMaxLength(200);
        address.setRequiredIndicatorVisible(true);
        address.addClassNames("mb-s");

        Div subSection = new Div();
        subSection.addClassNames("flex", "flex-wrap", "gap-m");

        TextField postalCode = new TextField("Postal Code");
        postalCode.setRequiredIndicatorVisible(true);
        postalCode.setPattern("[\\d \\p{L}]*");
        postalCode.addClassNames("mb-s");

        TextField city = new TextField("City");
        city.setRequiredIndicatorVisible(true);
        city.addClassNames("flex-grow", "mb-s");

        subSection.add(postalCode, city);

        ComboBox stateSelect = new ComboBox("State");
        stateSelect.setRequiredIndicatorVisible(true);

        stateSelect.setItems(states);
        stateSelect.setVisible(false);
        countrySelect.setItems(countries);
        countrySelect.addValueChangeListener(e -> {
            stateSelect.setVisible(countrySelect.getValue().equals("United States"));
        });

        Checkbox sameAddress = new Checkbox("Billing address is the same as shipping address");
        sameAddress.addClassNames("mt-s");

        Checkbox rememberAddress = new Checkbox("Remember address for next time");

        shippingDetails.add(stepTwo, header, countrySelect, address, subSection, stateSelect, sameAddress,
                rememberAddress);
        return shippingDetails;
    }

    private Component createPaymentInformationSection() {
        Section paymentInfo = new Section();
        paymentInfo.addClassNames("flex", "flex-col", "mb-xl", "mt-m");

        Paragraph stepThree = new Paragraph("Checkout 3/3");
        stepThree.addClassNames("m-0", "text-s", "text-secondary");

        H3 header = new H3("Personal details");
        header.addClassNames("mb-m", "mt-s", "text-2xl");

        TextField cardHolder = new TextField("Cardholder name");
        cardHolder.setRequiredIndicatorVisible(true);
        cardHolder.setPattern("[\\p{L} \\-]+");
        cardHolder.addClassNames("mb-s");

        Div subSectionOne = new Div();
        subSectionOne.addClassNames("flex", "flex-wrap", "gap-m");

        TextField cardNumber = new TextField("Card Number");
        cardNumber.setRequiredIndicatorVisible(true);
        cardNumber.setPattern("[\\d ]{12,23}");
        cardNumber.addClassNames("mb-s");

        TextField securityCode = new TextField("Security Code");
        securityCode.setRequiredIndicatorVisible(true);
        securityCode.setPattern("[0-9]{3,4}");
        securityCode.addClassNames("flex-grow", "mb-s");
        securityCode.setHelperText("What is this?");

        subSectionOne.add(cardNumber, securityCode);

        Div subSectionTwo = new Div();
        subSectionTwo.addClassNames("flex", "flex-wrap", "gap-m");

        Select<String> expirationMonth = new Select<>();
        expirationMonth.setLabel("Expiration month");
        expirationMonth.setRequiredIndicatorVisible(true);
        expirationMonth.setItems("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

        Select<String> expirationYear = new Select<>();
        expirationYear.setLabel("Expiration month");
        expirationYear.setRequiredIndicatorVisible(true);
        expirationYear.setItems("22", "23", "24", "25", "26");

        subSectionTwo.add(expirationMonth, expirationYear);

        paymentInfo.add(stepThree, header, cardHolder, subSectionTwo);
        return paymentInfo;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames("flex", "items-center", "justify-between", "my-m");

        Button cancel = new Button("Cancel order");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button pay = new Button("Pay securely", new Icon(VaadinIcon.LOCK));
        pay.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        footer.add(cancel, pay);
        return footer;
    }

    private Aside createAside() {
        Aside aside = new Aside();
        aside.addClassNames("bg-contrast-5", "box-border", "p-l", "rounded-l", "sticky");
        Header headerSection = new Header();
        headerSection.addClassNames("flex", "items-center", "justify-between", "mb-m");
        H3 header = new H3("Order");
        header.addClassNames("m-0");
        Button edit = new Button("Edit");
        edit.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        headerSection.add(header, edit);

        UnorderedList ul = new UnorderedList();
        ul.addClassNames("list-none", "m-0", "p-0", "flex", "flex-col", "gap-m");

        ul.add(createListItem("Vanilla cracker", "With wholemeal flour", "$7.00"));
        ul.add(createListItem("Vanilla blueberry cake", "With blueberry jam", "$8.00"));
        ul.add(createListItem("Vanilla pastry", "With wholemeal flour", "$5.00"));

        aside.add(headerSection, ul);
        return aside;
    }

    private ListItem createListItem(String primary, String secondary, String price) {
        ListItem item = new ListItem();
        item.addClassNames("flex", "justify-between");

        Div subSection = new Div();
        subSection.addClassNames("flex", "flex-col");

        subSection.add(new Span(primary));
        Span secondarySpan = new Span(secondary);
        secondarySpan.addClassNames("text-s text-secondary");
        subSection.add(secondarySpan);

        Span priceSpan = new Span(price);

        item.add(subSection, priceSpan);
        return item;
    }
}
