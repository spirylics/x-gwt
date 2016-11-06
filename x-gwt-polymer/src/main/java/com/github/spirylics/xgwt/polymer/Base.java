package com.github.spirylics.xgwt.polymer;


import com.github.spirylics.xgwt.essential.Fn;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import jsinterop.annotations.JsType;

@JsType(isNative = true)
public interface Base {

    PolymerElement $$(String selector);

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

    void importHref(String href, Fn.ArgRet onload, Fn.Arg onerror);

    String resolveUrl(String url);

}
