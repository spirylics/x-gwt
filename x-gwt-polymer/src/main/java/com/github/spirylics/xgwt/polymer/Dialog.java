package com.github.spirylics.xgwt.polymer;

import com.google.common.base.Strings;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.Promise;
import com.google.gwt.query.client.js.JsUtils;
import com.google.gwt.user.client.History;

public class Dialog extends PolymerComponent implements HasOpenHandlers<Dialog>, HasCloseHandlers<Dialog> {
    String dialogParameter;

    public interface Events {
        String OVERLAY_OPENED = "iron-overlay-opened";
        String OVERLAY_CLOSED = "iron-overlay-closed";
    }

    public Dialog(String html) {
        super("paper-dialog", html);
        gQuery
                .attr("entry-animation", "scale-up-animation")
                .attr("exit-animation", "fade-out-animation")
                .attr("with-backdrop", "");
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (hasHistoryDialogParameter(event.getValue())) {
                    openOverlay().done(new Function() {
                        @Override
                        public void f() {
                            OpenEvent.fire(Dialog.this, Dialog.this);
                        }
                    });
                } else {
                    closeOverlay().done(new Function() {
                        @Override
                        public void f() {
                            CloseEvent.fire(Dialog.this, Dialog.this);
                        }
                    });
                }
            }
        });
        on(Events.OVERLAY_OPENED, new Function() {
            @Override
            public void f() {
                addDialogParameter();
            }
        });
        on(Events.OVERLAY_CLOSED, new Function() {
            @Override
            public void f() {
                removeDialogParameter();
            }
        });
    }

    public void setHistoryToken(String historyToken) {
        this.dialogParameter = ";dialog=" + historyToken;
    }

    public Dialog open() {
        if (hasHistoryDialogParameter(History.getToken())) {
            openOverlay();
        } else {
            addDialogParameter();
        }
        return this;
    }

    public Dialog close() {
        if (hasHistoryDialogParameter(History.getToken())) {
            removeDialogParameter();
        } else {
            closeOverlay();
        }
        return this;
    }

    public boolean isOpened() {
        return gQuery.prop("opened");
    }

    public ClosingReason getClosingReason() {
        return JsUtils.prop(getElement(), "closingReason");
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
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                if (!hasHistoryDialogParameter(History.getToken())) {
                    History.newItem(History.getToken() + getDialogParameter(), true);
                }
            }
        });
        return this;
    }

    void removeDialogParameter() {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                if (hasHistoryDialogParameter(History.getToken())) {
                    History.back();
                }
            }
        });
    }

    Promise openOverlay() {
        return overlay("open", Events.OVERLAY_OPENED);
    }

    Promise closeOverlay() {
        return overlay("close", Events.OVERLAY_CLOSED);
    }

    Promise overlay(String action, String event) {
        Promise promise = whenEvent(event);
        invoke(Lifecycle.State.attached, action);
        return promise;
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
