package com.example.simplehr02.view.basic;

import com.example.simplehr02.data.basic.service.UserService;
import com.example.simplehr02.view.MainView;
import com.example.simplehr02.view.common.LazyComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.Route;


@Route(value = "/admin", layout = MainView.class)
public class AdminView extends Div {

    private TabSheet tabSheet = new TabSheet();

    public AdminView(UserService userService) {
        tabSheet.add("Users", new LazyComponent(() -> new UserView(userService)));
        tabSheet.add("Others", new LazyComponent(() -> new Text("Other")));
        add(tabSheet);
    }

}
