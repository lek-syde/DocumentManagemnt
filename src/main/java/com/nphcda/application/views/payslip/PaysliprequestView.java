package com.nphcda.application.views.payslip;

import com.nphcda.application.data.entity.Document;
import com.nphcda.application.data.entity.PaySlipRequest;
import com.nphcda.application.data.service.DBFileStorageService;
import com.nphcda.application.data.service.PaySlipRequestService;
import com.nphcda.application.views.MainLayout;
import com.nphcda.application.views.document.PayslipForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@Component
@Scope("prototype")
@Route(value="payslip-request", layout = MainLayout.class)
@PageTitle("Payslip Requests | NPHCDA")
@RolesAllowed("admin")
public class PaysliprequestView extends VerticalLayout {
    Grid<PaySlipRequest> grid = new Grid<>(PaySlipRequest.class);
    TextField filterText = new TextField();
    Button addContactButton = new Button("Add Payslip");
    PayslipListForm form;


    PaySlipRequestService service;

    public PaysliprequestView(@Autowired PaySlipRequestService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

       form = new PayslipListForm(service.listAll());
        form.setWidth("25em");
        form.addListener(PayslipForm.SaveEvent.class, this::saveContact);
        form.addListener(PayslipForm.DeleteEvent.class, this::deleteContact);
        form.addListener(PayslipForm.CloseEvent.class, e -> closeEditor());

        FlexLayout content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();

        add(content);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event ->
            editContact(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("requestType", "startYear", "startMonth", "endYear","endMonth");
        grid.addColumn(new ComponentRenderer<>(person -> {
            if (person.isFuifilled()) {
                return new Icon(VaadinIcon.CHECK);
            } else {
                return new Icon(VaadinIcon.CLOSE);
            }
        })).setHeader("Send");

        grid.addColumn(new TextRenderer<>(person -> {
           return person.getUserdetails().getFirstName()+","+ person.getUserdetails().getLastName();
        })).setHeader("Requester");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());


        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveContact(PayslipForm.SaveEvent event) {
        System.out.println(event.getContact().toString());
       // service.storeFile(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(PayslipForm.DeleteEvent event) {
        //service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    public void editContact(PaySlipRequest contact) {
        if (contact == null) {
            closeEditor();
        } else {
           form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addContact() {
        grid.asSingleSelect().clear();
        editContact(new PaySlipRequest());
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.listAll());
    }


}
