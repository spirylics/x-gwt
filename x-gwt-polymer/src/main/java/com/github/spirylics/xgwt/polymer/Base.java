package com.github.spirylics.xgwt.polymer;


import com.github.spirylics.xgwt.essential.Element;
import com.github.spirylics.xgwt.essential.Error;
import com.github.spirylics.xgwt.essential.Fn;
import com.github.spirylics.xgwt.essential.Promise;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Event;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import java.util.Arrays;

@JsType(isNative = true)
public interface Base {

    <E extends Element> E $$(String selector);

    void toggleClass(String name, boolean b, Element node);

    void toggleAttribute(String name, boolean b, Element node);

    void attributeFollows(String name, Element newNode, Element oldNode);

    void classFollows(String name, Element newNode, Element oldNode);

    void fire(String type, JavaScriptObject detail, JavaScriptObject options);

    Number async(Fn.NoArg method);

    Number async(Fn.NoArg method, int wait);

    void cancelAsync(Number handle);

    void transform(String transform, Element node);

    void translate3d(String x, String y, String z, Element node);

    void importHref(String href, Fn.Arg<Event> onload, Fn.Arg<Error> onerror);

    String resolveUrl(String url);

    Event fire(String type);

    @JsOverlay
    default Promise<Event, Error> xImportHref(String href) {
        return new Promise<>(
                (resolve, reject) -> importHref(href, event -> resolve.e(event)
                        , error -> reject.e(error)));
    }

    @JsOverlay
    default Promise xImportHrefs(String... hrefs) {
        return Promise.all((Promise[]) Arrays.stream(hrefs).map(href -> xImportHref(href)).toArray());
    }
}
