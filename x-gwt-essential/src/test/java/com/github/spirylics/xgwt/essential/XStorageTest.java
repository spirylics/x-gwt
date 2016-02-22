package com.github.spirylics.xgwt.essential;

import com.google.gwt.storage.client.Storage;
import com.google.gwtmockito.GwtMock;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.web.bindery.event.shared.EventBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

@RunWith(GwtMockitoTestRunner.class)
public class XStorageTest {

    @GwtMock
    Storage storage;
    @Mock
    XMapper xMapper;
    @Mock
    EventBus eventBus;
    @InjectMocks
    XStorage xStorage;


    @Test
    public void testSave_withStar() {
        xStorage.save(new Object());

        Mockito.verify(storage).setItem(Matchers.anyString(), Matchers.anyString());
    }

    @Test
    public void testDelete_withStar() {
        xStorage.delete(new Object());

        Mockito.verify(storage).removeItem(Matchers.anyString());
    }
}
