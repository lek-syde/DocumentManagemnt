package com.nphcda.application.views.document;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.shared.Registration;
import com.nphcda.application.data.entity.Document;

import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;


public class PayslipForm extends FormLayout {
  private Document document;

  TextArea notes = new TextArea("Notes");
  ComboBox<String> year = new ComboBox<>("Year");
  ComboBox<String> month = new ComboBox<>("month");
  Binder<Document> binder = new Binder<Document>(Document.class);
  Upload data;

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public PayslipForm(List<Document> companies)  {
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    data = new Upload(buffer);


    addClassName("contact-form");
    binder.bindInstanceFields(this);
    year.setItems("2019","2020","2021", "2022");
    month.setItems("January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    Image img = new Image("images/ebook.png", "file");
    img.setWidth("200px");




    data.getStyle().set("margin-top","5px");



    data.setAcceptedFileTypes("application/pdf", ".pdf");
    add(notes,
        year,
        month,
        data,
        createButtonsLayout());






    binder.forField(year).asRequired()
            .bind(Document::getYear, Document::setYear);
    binder.forField(notes).asRequired()
            .bind(Document::getYear, Document::setYear);

    binder.forField(month).asRequired()
            .bind(Document::getYear, Document::setYear);



    data.addSucceededListener(event -> {
      String fileName = event.getFileName();
      InputStream inputStream = buffer.getInputStream(fileName);
      try {
        document.setData(inputStream.readAllBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
      System.out.println("do something");
      // Do something with the file data
      // processFile(inputStream, fileName);
    });
    data.addFileRejectedListener(event -> {
      String errorMessage = event.getErrorMessage();

      Notification notification = Notification.show(
              errorMessage,
              5000,
              Notification.Position.MIDDLE
      );
      notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    });
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, document)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setContact(Document contact) {
    this.document = contact;
    binder.readBean(contact);


  }

  private void validateAndSave() {
    try {
      binder.writeBean(document);
      if(document.getData()!=null){
        fireEvent(new SaveEvent(this, document));
      }else{
        Notification notification = Notification.show(
                "please select a file",
                5000,
                Notification.Position.MIDDLE
        );
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
      }

    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class ContactFormEvent extends ComponentEvent<PayslipForm> {
    private Document contact;

    protected ContactFormEvent(PayslipForm source, Document contact) {
      super(source, false);
      this.contact = contact;
    }

    public Document getContact() {
      return contact;
    }
  }

  public static class SaveEvent extends ContactFormEvent {
    SaveEvent(PayslipForm source, Document contact) {
      super(source, contact);
    }
  }

  public static class DeleteEvent extends ContactFormEvent {
    DeleteEvent(PayslipForm source, Document contact) {
      super(source, contact);
    }

  }

  public static class CloseEvent extends ContactFormEvent {
    CloseEvent(PayslipForm source) {
      super(source, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}