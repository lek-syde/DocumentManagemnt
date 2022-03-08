package com.nphcda.application.views.document;

import com.nphcda.application.data.entity.Document;
import com.nphcda.application.data.service.DBFileStorageService;
import com.nphcda.application.views.MainLayout;
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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@Component
@Scope("prototype")
@Route(value="pay-slip", layout = MainLayout.class)
@PageTitle("Contacts | NPHCDA")
@RolesAllowed("admin")
public class ListView extends VerticalLayout {
    Grid<Document> grid = new Grid<>(Document.class);
    TextField filterText = new TextField();
    Button addContactButton = new Button("Add Payslip");
    PayslipForm form;


    DBFileStorageService service;

    public ListView(@Autowired DBFileStorageService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new PayslipForm(service.listAll());
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

        add(getToolbar(), content);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event ->
            editContact(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("year", "month", "notes");
        grid.addColumn(new ComponentRenderer<>(person -> {
            if (person.getData() != null) {
                Anchor sd= new Anchor("/pdf/"+person.getId()+".pdf", "view");
                sd.setTarget("_BLANK");
                return sd;
            } else {
                return new Icon(VaadinIcon.FILE);
            }
        })).setHeader("File");
        //grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        //grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
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
        service.storeFile(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(PayslipForm.DeleteEvent event) {
        //service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    public void editContact(Document contact) {
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
        editContact(new Document());
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
