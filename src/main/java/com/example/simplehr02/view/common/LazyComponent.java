package com.example.simplehr02.view.common;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.SerializableSupplier;

public class LazyComponent extends Div{
    public LazyComponent(SerializableSupplier<? extends Component> supplier) {
        addAttachListener(e -> {
            if (getElement().getChildCount() == 0) {
                add(supplier.get());
            }
        });
    }
}
