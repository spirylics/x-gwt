package com.github.spirylics.xgwt.polymer;

import com.google.gwt.user.client.ui.HTMLPanel;

public class PolymerWidget extends HTMLPanel {

    public PolymerWidget(String tag) {
        super(tag, "");
    }

    public PolymerWidget(String tag, String html) {
        super(tag, html);
    }

    public void setAttributes(String attributes) {
        String[] attributeArray = attributes.split(" ");
        for (String attribute : attributeArray) {
            String[] a = attribute.split(":");
            p().attribute(a[0], a[1]);
        }
    }

    public PolymerElement p() {
        return (PolymerElement) getElement();
    }

}
