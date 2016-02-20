package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.Mapper;
import com.google.gwt.core.client.JavaScriptObject;

public class XSnapshot {

    static Mapper mapper;

    final Snapshot<JavaScriptObject> snapshot;

    public XSnapshot(Snapshot<JavaScriptObject> snapshot) {
        this.snapshot = snapshot;
    }

    public String key() {
        return snapshot.key();
    }

    public <V> V val(Class<V> clazz) {
        return mapper.readJso(snapshot.val(), clazz);
    }

    public boolean forEach(final Fn.Arg<XSnapshot> fn) {
        return snapshot.forEach(new Fn.Arg<Snapshot<JavaScriptObject>>() {
            @Override
            public void e(Snapshot<JavaScriptObject> snapshot) {
                fn.e(new XSnapshot(snapshot));
            }
        });
    }
}
