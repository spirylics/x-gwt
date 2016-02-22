package com.github.spirylics.xgwt.essential;

import com.google.gwt.event.shared.EventHandler;

public interface ModelHandler<D> extends EventHandler {
    void onDeleteModel(D deleted);

    void onUpdateModel(D old, D updated);

    void onCreateModel(D created);

    void onClearModel();
}
