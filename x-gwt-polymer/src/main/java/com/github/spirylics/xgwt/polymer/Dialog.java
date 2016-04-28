package com.github.spirylics.xgwt.polymer;

import com.google.common.base.Strings;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTMLPanel;

import java.util.logging.Logger;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.body;

public class Dialog extends HTMLPanel implements HasOpenHandlers<Dialog>, HasCloseHandlers<Dialog> {

    static Dialog main;

    final String dialogParameter;
    HandlerRegistration onceCloseRegistration;
    HandlerRegistration onceOpenRegistration;
    public final Lifecycle lifecycle;

    public Dialog(final String historyToken) {
        super("paper-dialog", "<h2 class=\"dialog-header\"></h2>" +
                "<paper-dialog-scrollable class=\"dialog-content\"></paper-dialog-scrollable>" +
                "<div class=\"buttons\">" +
                "<paper-button dialog-dismiss>OK</paper-button>" +
                "<paper-button dialog-confirm autofocus>OK</paper-button>" +
                "</div>");
        this.lifecycle = new Lifecycle(getElement());
        $(this)
                .attr("entry-animation", "scale-up-animation")
                .attr("exit-animation", "fade-out-animation")
                .attr("with-backdrop", "");
        if (historyToken == null) {
            dialogParameter = null;
        } else {
            dialogParameter = ";dialog=" + historyToken;
            History.addValueChangeHandler(new ValueChangeHandler<String>() {
                @Override
                public void onValueChange(ValueChangeEvent<String> event) {
                    Logger.getLogger("").severe(event.getValue());
                    if (hasHistoryDialogParameter(event.getValue())) {
                        uiOpen().done(new Function() {
                            @Override
                            public void f() {
                                OpenEvent.fire(Dialog.this, Dialog.this);
                            }
                        });
                    } else {
                        uiClose().done(new Function() {
                            @Override
                            public void f() {
                                CloseEvent.fire(Dialog.this, Dialog.this);
                            }
                        });
                    }
                }
            });
            onUiOpen(new Function() {
                @Override
                public void f() {
                    addDialogParameter();
                }
            });
            onUiClose(new Function() {
                @Override
                public void f() {
                    removeDialogParameter();
                }
            });
        }
    }

    private final boolean hasDialogParameter() {
        return !Strings.isNullOrEmpty(dialogParameter);
    }

    private final boolean hasHistoryDialogParameter(String historyToken) {
        return hasDialogParameter() && Strings.nullToEmpty(historyToken).contains(dialogParameter);
    }

    private final Dialog addDialogParameter() {
        if (!hasHistoryDialogParameter(History.getToken())) {
            History.newItem(History.getToken() + dialogParameter, true);
        }
        return this;
    }

    private final Dialog removeDialogParameter() {
        if (hasHistoryDialogParameter(History.getToken())) {
            History.back();
        }
        return this;
    }

    public static Dialog main() {
        if (main == null) {
            main = new Dialog("main");
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
        if (hasDialogParameter()) {
            uiOpen();
        } else {
            addDialogParameter();
        }
        return this;
    }

    public Dialog close() {
        if (hasDialogParameter()) {
            removeDialogParameter();
        } else {
            uiClose();
        }
        return this;
    }

    public boolean isOpened() {
        return JsUtils.prop(getElement(), "opened");
    }

    public ClosingReason getClosingReason() {
        return JsUtils.prop(getElement(), "closingReason");
    }

    Promise uiOpen() {
        final Promise.Deferred deferred = GQuery.Deferred();
        if (isOpened()) {
            deferred.resolve();
        } else {
            onceUiOpen(new Function() {
                @Override
                public void f() {
                    deferred.resolve();
                }
            });
            JsUtils.jsni(getElement(), "open");
        }
        return deferred.promise();
    }

    Promise uiClose() {
        final Promise.Deferred deferred = GQuery.Deferred();
        if (isOpened()) {
            onceUiClose(new Function() {
                @Override
                public void f() {
                    deferred.resolve();
                }
            });
            JsUtils.jsni(getElement(), "close");
        } else {
            deferred.resolve();
        }
        return deferred.promise();
    }

    public Dialog onceUiOpen(final Function function) {
        return onUiOpen(new Function() {
            @Override
            public void f() {
                function.f(getEvent(), getArguments());
                offUiOpen(this);
            }
        });
    }

    public Dialog onceUiClose(final Function function) {
        return onUiClose(new Function() {
            @Override
            public void f() {
                function.f(getEvent(), getArguments());
                offUiClose(this);
            }
        });
    }

    public Dialog onUiOpen(Function function) {
        return on("iron-overlay-opened", function);
    }

    public Dialog onUiClose(Function function) {
        return on("iron-overlay-closed", function);
    }

    public Dialog offUiOpen(Function function) {
        return off("iron-overlay-opened", function);
    }

    public Dialog offUiClose(Function function) {
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

    @Override
    public HandlerRegistration addOpenHandler(OpenHandler<Dialog> handler) {
        return addHandler(handler, OpenEvent.getType());
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<Dialog> handler) {
        return addHandler(handler, CloseEvent.getType());
    }

    public Dialog onceOpenHandler(final OpenHandler<Dialog> handler) {
        this.onceOpenRegistration = addHandler(new OpenHandler<Dialog>() {
            @Override
            public void onOpen(OpenEvent<Dialog> event) {
                handler.onOpen(event);
                onceOpenRegistration.removeHandler();
            }
        }, OpenEvent.getType());
        return this;
    }

    public Dialog onceCloseHandler(final CloseHandler<Dialog> handler) {
        this.onceCloseRegistration = addHandler(new CloseHandler() {
            @Override
            public void onClose(CloseEvent event) {
                handler.onClose(event);
                onceCloseRegistration.removeHandler();
            }
        }, CloseEvent.getType());
        return this;
    }
}
