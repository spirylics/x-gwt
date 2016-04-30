package com.github.spirylics.xgwt.polymer;

import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.ui.HTMLPanel;

import static com.google.gwt.query.client.GQuery.$;

public class PolymerPanel extends HTMLPanel {
    public final Lifecycle lifecycle;
    public final GQuery gQuery;

    public PolymerPanel(String tag, String html) {
        super(tag, html);
        this.lifecycle = XPolymer.lifecycle(getElement());
        this.gQuery = $(this);
    }

    public <T> T invoke(String method, Object... args) {
        return JsUtils.jsni(getElement(), method, args);
    }

    public Promise invoke(Lifecycle.State state, final String method, final Object... args) {
        return lifecycle.promise(state).then(new Function() {
            @Override
            public Object f(Object... arguments) {
                return invoke(method, args);
            }
        });
    }
}
