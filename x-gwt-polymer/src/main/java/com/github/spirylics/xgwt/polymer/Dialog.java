package com.github.spirylics.xgwt.polymer;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTMLPanel;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;

public class Dialog extends HTMLPanel {

    static Dialog main;

    public Dialog() {
        super("paper-dialog", "<h2 class=\"dialog-header\"></h2>" +
                "<paper-dialog-scrollable class=\"dialog-content\"></paper-dialog-scrollable>" +
                "<div class=\"buttons\">" +
                "<paper-button dialog-dismiss>OK</paper-button>" +
                "<paper-button dialog-confirm autofocus>OK</paper-button>" +
                "</div>");
        $(this)
                .attr("entry-animation", "scale-up-animation")
                .attr("exit-animation", "fade-out-animation")
                .attr("with-backdrop", "");
    }

    public Dialog(final String historyToken) {
        this();
        final String dialogParameter = ";dialog=" + historyToken;
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (!event.getValue().contains(dialogParameter)) {
                    close();
                } else {
                    open();
                }
            }
        });
        onOpen(new Function() {
            @Override
            public void f() {
                if (!History.getToken().contains(dialogParameter)) {
                    History.newItem(History.getToken() + dialogParameter, false);
                }
            }
        }).onClose(new Function() {
            @Override
            public void f() {
                if (History.getToken().contains(dialogParameter)) {
                    History.back();
                }
            }
        });
    }

    public static Dialog main() {
        if (main == null) {
            main = new Dialog("dialog_main");
            $(body).append(main.getElement());
        }
        return main;
    }

    public Dialog setHeader(String html) {
        GQuery header = $(this).find(".dialog-header").empty();
        if (html == null) {
            header.hide();
        } else {
            header.html(html).show();
        }
        return this;
    }

    public Dialog setContent(String html) {
        GQuery content = $(this).find(".dialog-content").empty();
        if (content == null) {
            content.hide();
        } else {
            content.html(html).show();
        }
        return this;
    }

    public Dialog open() {
        JsUtils.jsni(getElement(), "open");
        return this;
    }

    public Dialog close() {
        JsUtils.jsni(getElement(), "close");
        return this;
    }

    public Dialog onceOpen(final Function function) {
        return onOpen(new Function() {
            @Override
            public void f() {
                function.f(getEvent(), getArguments());
                offOpen(this);
            }
        });
    }

    public Dialog onceClose(final Function function) {
        return onClose(new Function() {
            @Override
            public void f() {
                function.f(getEvent(), getArguments());
                offClose(this);
            }
        });
    }

    public Dialog onOpen(Function function) {
        return on("iron-overlay-opened", function);
    }

    public Dialog onClose(Function function) {
        return on("iron-overlay-closed", function);
    }

    public Dialog offOpen(Function function) {
        return off("iron-overlay-opened", function);
    }

    public Dialog offClose(Function function) {
        return off("iron-overlay-closed", function);
    }

    public Dialog off(String event, Function function) {
        $(this).off(event, function);
        return this;
    }

    public Dialog on(String event, Function function) {
        $(this).on(event, function);
        return this;
    }

}
