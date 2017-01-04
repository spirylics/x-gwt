package com.github.spirylics.xgwt.polymer;

import com.github.spirylics.xgwt.essential.Error;
import com.github.spirylics.xgwt.essential.Promise;
import com.github.spirylics.xgwt.essential.XGWT;
import com.google.gwt.dom.client.Element;

public class Lifecycle {

    public enum State {
        detached, attached, ready(attached), created(ready);

        final State[] involvedStates;

        State(State... involvedStates) {
            this.involvedStates = involvedStates;
        }

        boolean isResolvedWithByInvolved(State state) {
            for (State i : involvedStates) {
                if (i.isResolvedWith(state)) {
                    return true;
                }
            }
            return false;
        }

        boolean isResolvedWith(State state) {
            return this.equals(state) || isResolvedWithByInvolved(state);
        }
    }

    final PolymerElement polymerElement;
    private State state;

    public Lifecycle(final Element element) {
        this((PolymerElement) element);
    }

    public Lifecycle(final PolymerElement polymerElement) {
        this.polymerElement = polymerElement;
        checkState();
        life(State.values());
    }

    public final Promise<State, Error> promise(State state) {
        return new Promise<>((resolve, reject) -> {
            if (state.isResolvedWith(getState())) {
                resolve.e(getState());
            } else {
                Polymer.dom().flush();
                XGWT.extend(this.polymerElement, state.name(), o -> {
                    resolve.e(getState());
                    return null;
                });
            }
        });
    }

    private void life(final State... states) {
        for (State state : states) {
            life(state);
        }
    }

    private void life(final State state) {
        Polymer.dom().flush();
        XGWT.extend(this.polymerElement, state.name(), o -> {
            setState(state);
            return null;
        });
    }

    private void checkState() {
        if (polymerElement.el().getPropertyBoolean("isAttached")) {
            setState(State.attached);
        }
    }

    public State getState() {
        checkState();
        return this.state;
    }

    private void setState(State state) {
        this.state = state;
    }
}
