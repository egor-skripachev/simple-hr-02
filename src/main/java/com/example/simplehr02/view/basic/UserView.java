package com.example.simplehr02.view.basic;

import com.example.simplehr02.data.basic.entity.UserEntity;
import com.example.simplehr02.data.basic.exception.UserAlreadyExistException;
import com.vaadin.flow.component.html.Div;

import com.example.simplehr02.data.basic.exception.UserNotFoundException;
import com.example.simplehr02.data.basic.model.User;
import com.example.simplehr02.data.basic.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import java.util.ArrayList;
import java.util.List;

public class UserView extends Div {

    private List<User> users = new ArrayList<User>();
    private Grid<User> grid = new Grid<>(User.class, false);
    private Div hint = new Div();
    private Button createButton = new Button();
    private Dialog dialog;
    private UserService userService;


    public UserView(UserService userService) {
        this.userService = userService;

        setupGrid();
        refreshGrid();
        setupCreateButton();
        add(grid, hint, createButton);
    }

    private void setupGrid() {
        grid.addColumn(User::getId).setHeader("ID").setAutoWidth(true).setFlexGrow(0);
        grid.addColumn(User::getUsername).setHeader("Username");
        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, user) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_CONTRAST,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> {
                        setupEditDialog(user);
                        dialog.open();
                    });
                    button.setIcon(new Icon(VaadinIcon.EDIT));
                })).setHeader("Edit").setAutoWidth(true).setFlexGrow(0);


        try {
            users = userService.getAll();
        } catch (Exception e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

        grid.setMaxWidth("70em");
        grid.setItems(users);
        grid.setAllRowsVisible(true);

        hint.setText("Users not found");
        hint.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");
    }

    private void setupCreateButton() {
        createButton.setIcon(new Icon(VaadinIcon.PLUS));
        createButton.setText("Add");
        createButton.addClickListener(e -> {
            setupCreatedDialog();
            dialog.open();
        });
    }

    private void setupCreatedDialog() {
        dialog = new Dialog();
        dialog.setHeaderTitle("Create");

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        username.setRequired(true);
        password.setRequired(true);

        VerticalLayout dialogLayout = new VerticalLayout(username, password);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "30rem").set("max-width", "100%");

        Button saveButton = new Button("Save", e -> {
            UserEntity user = new UserEntity();
            user.setUsername(username.getValue());
            user.setPassword(password.getValue());
            create(user);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        dialog.add(dialogLayout);
        dialog.getFooter().add(cancelButton, saveButton);
    }


    private void setupEditDialog(User user) {
        dialog = new Dialog();
        dialog.setHeaderTitle("Edit");

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());

        TextField id = new TextField("ID");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        username.setRequired(true);
        password.setRequired(true);

        id.setValue(entity.getId().toString());
        username.setValue(entity.getUsername());

        id.setReadOnly(true);
        password.setReadOnly(true);

        VerticalLayout dialogLayout = new VerticalLayout(id, username);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "30rem").set("max-width", "100%");

        Button saveButton = new Button("Save", e -> {
            entity.setUsername(username.getValue());
            if (!password.getValue().isEmpty()) {
                entity.setPassword(password.getValue());
            }
            update(entity);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button resetPasswordButton = new Button("Reset password", e -> {
            password.setReadOnly(false);
        });

        Button deleteButton = new Button("Delete", e -> {
            delete(user);
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        dialogLayout.add(password, resetPasswordButton, deleteButton);
        dialog.add(dialogLayout);
        dialog.getFooter().add(cancelButton, saveButton);
    }

    private void update(UserEntity user) {
        try {
            userService.update(user);
            users.stream().filter(u -> u.getId() == user.getId())
                    .forEach(u -> u.setUsername(user.getUsername()));
            refreshGrid();
            Notification notification = Notification.show("User updated");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            dialog.close();
        } catch (UserNotFoundException e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (UserAlreadyExistException e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (Exception e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void delete(User user) {
        if (user == null) return;

        try {
            userService.delete(user.getId());
            users.remove(user);
            refreshGrid();
            Notification notification = Notification.show("User deleted");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            dialog.close();
        } catch (UserNotFoundException e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (Exception e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void refreshGrid(){
        if (users.size() > 0) {
            grid.setVisible(true);
            hint.setVisible(false);
            grid.getDataProvider().refreshAll();
        } else {
            grid.setVisible(false);
            hint.setVisible(true);
        }
    }

    private void create(UserEntity user) {
        try {
            User newUser = userService.create(user);
            users.add(newUser);
            refreshGrid();
            Notification notification = Notification.show(String.format("User id %d created", newUser.getId()));
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            dialog.close();
        } catch (UserAlreadyExistException e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (Exception e) {
            Notification notification = Notification.show(e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

}