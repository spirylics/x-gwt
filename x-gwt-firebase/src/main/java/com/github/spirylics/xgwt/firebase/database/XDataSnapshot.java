package com.github.spirylics.xgwt.firebase.database;

import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.XMapper;

public class XDataSnapshot {

    public final DataSnapshot<?> dataSnapshot;

    public XDataSnapshot(DataSnapshot<?> dataSnapshot) {
        this.dataSnapshot = dataSnapshot;
    }

    public String getKey() {
        return dataSnapshot.getKey();
    }

    public boolean exists() {
        return dataSnapshot.exists();
    }

    public Reference getRef() {
        return dataSnapshot.getRef();
    }

    public <V> V val(Class<V> clazz) {
        return XMapper.get().convert(dataSnapshot.val(), clazz);
    }

    public boolean forEach(final Fn.Arg<XDataSnapshot> fn) {
        return dataSnapshot.forEach(new Fn.Arg<DataSnapshot<Object>>() {
            @Override
            public void e(DataSnapshot<Object> dataSnapshot) {
                fn.e(new XDataSnapshot(dataSnapshot));
            }
        });
    }

    public boolean hasChild(String path) {
        return dataSnapshot.hasChild(path);
    }
}
