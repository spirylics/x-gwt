package com.github.spirylics.xgwt.firebase;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.XMapper;

public class XSnapshot {

    static XMapper xMapper;

    public final Snapshot<?> snapshot;

    public XSnapshot(Snapshot<?> snapshot) {
        this.snapshot = snapshot;
    }

    public String key() {
        return snapshot.key();
    }

    public boolean exists() {
        return snapshot.exists();
    }

    public Firebase ref() {
        return snapshot.ref();
    }

    public <V> V val(Class<V> clazz) {
        return xMapper.convert(snapshot.val(), clazz);
    }

    public boolean forEach(final Fn.Arg<XSnapshot> fn) {
        return snapshot.forEach(new Fn.Arg<Snapshot<Object>>() {
            @Override
            public void e(Snapshot<Object> snapshot) {
                fn.e(new XSnapshot(snapshot));
            }
        });
    }
}
