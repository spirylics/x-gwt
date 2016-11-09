package com.github.spirylics.xgwt.polymer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.HTMLPanel;

public class PolymerWidget extends HTMLPanel {
    final PolymerWrapper w;

    public PolymerWidget(String tag) {
        this(tag, "");
    }

    public PolymerWidget(String tag, String html) {
        super(tag, html);
        this.w = new PolymerWrapper(getElement());
    }

    public PolymerWrapper w() {
        return this.w;
    }

    public GQuery $() {
        return w().$();
    }

    public void setAttributes(String attributes) {
        String[] attributeArray = attributes.split(" ");
        for (String attribute : attributeArray) {
            String[] a = attribute.split(":");
            w().setAttribute(a[0], a[1]);
        }
    }

}
