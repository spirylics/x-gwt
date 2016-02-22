package com.github.spirylics.xgwt.essential;


import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.EventBus;

import java.util.HashMap;
import java.util.Map;

public class ModelEvent<D> extends GwtEvent<ModelHandler<D>> {
    public static final Map<Class<?>, Type<ModelHandler<?>>> TYPES = new HashMap<>();

    public static <D> Type getType(Class<D> clazz) {
        if (!TYPES.containsKey(clazz)) {
            TYPES.put(clazz, new Type<ModelHandler<?>>());
        }
        return TYPES.get(clazz);
    }

    public static <D> void fire(EventBus source, Class<D> clazz, D old, D data) {
        source.fireEvent(new ModelEvent(clazz, old, data));
    }

    @Override
    public Type<ModelHandler<D>> getAssociatedType() {
        return getType(clazz);
    }

    @Override
    protected void dispatch(ModelHandler<D> handler) {
        if (old == null) {
            handler.onCreateModel(data);
        } else {
            if (data == null) {
                handler.onDeleteModel(old);
            } else {
                handler.onUpdateModel(old, data);
            }
        }
    }

    final Class<D> clazz;
    final D old;
    final D data;

    public ModelEvent(Class<D> clazz, D old, D data) {
        this.clazz = clazz;
        this.old = old;
        this.data = data;
    }
}