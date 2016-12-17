package com.github.spirylics.xgwt.polymer;

import com.github.spirylics.xgwt.essential.Error;
import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.Promise;
import com.google.common.base.Strings;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

import java.util.function.Predicate;

public class Dialog extends PolymerWidget implements HasOpenHandlers<Dialog>, HasCloseHandlers<Dialog> {
    @JsType(isNative = true)
    public interface ClosingReason {
        @JsProperty
        boolean isCanceled();

        @JsProperty
        boolean isConfirmed();

    }

    String dialogParameter;
    HandlerRegistration historyHandlerRegistration;
    HandlerRegistration openedHandlerRegistration;

    public Dialog(String html) {
        super("paper-dialog", html);
        p().attribute("entry-animation", "scale-up-animation")
                .attribute("exit-animation", "fade-out-animation")
                .attribute("with-backdrop", "");
    }

    void enableHistory() {
        if (this.historyHandlerRegistration == null) {
            this.historyHandlerRegistration = History.addValueChangeHandler(event -> overlay(hasHistoryDialogParameter(event.getValue()))
                    .then(opened -> {
                        if (opened) {
                            OpenEvent.fire(Dialog.this, Dialog.this);
                        } else {
                            disableHistory();
                            CloseEvent.fire(Dialog.this, Dialog.this);
                        }
                    }));
        }
        if (this.openedHandlerRegistration == null) {
            openedHandlerRegistration = p().onPropertyChange("opened", (Fn.Arg<Boolean>) opened -> {
                if (opened) {
                    addDialogParameter();
                } else {
                    removeDialogParameter();
                }
            });
        }
    }

    void disableHistory() {
        if (this.historyHandlerRegistration != null) {
            this.historyHandlerRegistration.removeHandler();
            this.historyHandlerRegistration = null;
        }
        if (this.openedHandlerRegistration != null) {
            this.openedHandlerRegistration.removeHandler();
            this.openedHandlerRegistration = null;
        }
    }

    public void setHistoryToken(String historyToken) {
        this.dialogParameter = "dialog=" + historyToken;
    }

    public Dialog open() {
        enableHistory();
        if (hasHistoryDialogParameter(History.getToken())) {
            overlay(true);
        } else {
            addDialogParameter();
        }
        return this;
    }

    public Dialog close() {
        if (hasHistoryDialogParameter(History.getToken())) {
            removeDialogParameter();
        } else {
            overlay(false);
        }
        return this;
    }

    public boolean isOpened() {
        return p().get("opened");
    }

    public ClosingReason getClosingReason() {
        return p().get("closingReason");
    }

    String getDialogParameter() {
        if (Strings.isNullOrEmpty(dialogParameter)) {
            throw new IllegalStateException("no history token defined!");
        }
        return dialogParameter;
    }

    boolean hasHistoryDialogParameter(String historyToken) {
        return Strings.nullToEmpty(historyToken).contains(getDialogParameter());
    }

    Dialog addDialogParameter() {
        Scheduler.get().scheduleDeferred(() -> {
            if (!hasHistoryDialogParameter(History.getToken())) {
                History.newItem(History.getToken()
                        + (Strings.isNullOrEmpty(History.getToken()) ? "#" : ";")
                        + getDialogParameter(), true);
            }
        });
        return this;
    }

    void removeDialogParameter() {
        Scheduler.get().scheduleDeferred(() -> {
            if (hasHistoryDialogParameter(History.getToken())) {
                History.back();
            }
        });
    }

    Promise<Boolean, Error> overlay(final boolean opened) {
        if (isOpened() != opened) {
            p().set("opened", opened);
        }
        return p().whenProperty("opened", (Predicate<Boolean>) pOpened -> pOpened == opened);
    }

    @Override
    public HandlerRegistration addOpenHandler(OpenHandler<Dialog> handler) {
        return addHandler(handler, OpenEvent.getType());
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<Dialog> handler) {
        return addHandler(handler, CloseEvent.getType());
    }
}
