package com.github.spirylics.xgwt.firebase.database;


import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.XMapper;

public class XDataSnapshot {

    public final DataSnapshot<?> dataSnapshot;

    public XDataSnapshot(DataSnapshot<?> dataSnapshot) {
        this.dataSnapshot = dataSnapshot;
    }

    public XDataSnapshot child(String path) {
        return new XDataSnapshot(dataSnapshot.child(path));
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
        return dataSnapshot.forEach(dataSnapshot1 -> fn.e(new XDataSnapshot(dataSnapshot1)));
    }

    public boolean hasChild(String path) {
        return dataSnapshot.hasChild(path);
    }

    public int numChildren() {
        return dataSnapshot.numChildren();
    }
}
