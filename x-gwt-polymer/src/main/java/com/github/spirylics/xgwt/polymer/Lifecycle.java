package com.github.spirylics.xgwt.polymer;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.query.client.Promise;

import java.util.Map;

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

    final Element element;
    State state;
    final Map<State, Promise> statePromiseMap = Maps.newHashMap();

    public Lifecycle(final Element element) {
        this.element = element;
        life(State.values());
    }

    public final Promise promise(State state) {
        if (state.isResolvedWith(this.state)) {
            return GQuery.Deferred().resolve().promise();
        } else {
            return statePromiseMap.get(state);
        }
    }

    private void life(final State... states) {
        for (State state : states) {
            life(state);
        }
    }

    private void life(final State state) {
        statePromiseMap.put(state, XPolymer.promise(this.element, state.name()).then(new Function() {
            @Override
            public void f() {
                Lifecycle.this.state = state;
            }
        }));
    }
}
