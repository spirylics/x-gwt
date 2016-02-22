package com.github.spirylics.xgwt.essential;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.builders.JsonBuilder;
import com.google.gwt.query.client.js.JsObjectArray;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class XMapper {

    final Map<Class<?>, ObjectMapper<?>> mappers;
    final Function<JsonBuilder, JavaScriptObject> jsonBuilderToJs = new Function<JsonBuilder, JavaScriptObject>() {
        @Nullable
        @Override
        public JavaScriptObject apply(JsonBuilder input) {
            return toJso(input);
        }
    };

    public XMapper(Map<Class<?>, ObjectMapper<?>> mappers) {
        this.mappers = mappers;
    }

    public <T> String write(T t) {
        if (t == null) {
            return null;
        }
        return getMapper(t).write(t);
    }

    public <T> JavaScriptObject writeJso(T t) {
        return JsonUtils.safeEval(write(t));
    }

    public <T> T read(String input, Class<T> clazz) {
        if (Strings.isNullOrEmpty(input)) {
            return null;
        }
        return getMapper(clazz).read(input);
    }

    public <T> T readJso(JavaScriptObject jso, Class<T> clazz) {
        return read(JsonUtils.stringify(jso), clazz);
    }

    public JavaScriptObject toJso(JsonBuilder jsonBuilder) {
        return JsonUtils.safeEval(jsonBuilder.toJson());
    }

    public <T> T fromJso(JavaScriptObject jso, Class clazz) {
        return GQ.create(clazz).parse(JsonUtils.stringify(jso));
    }

    public <T> JavaScriptObject toJso(T t) {
        if (t == null) {
            return null;
        }
        return JsonUtils.safeEval(getMapper(t).write(t));
    }

    public <T> JsObjectArray<JavaScriptObject> toJsoArray(List<T> tList) {
        JsObjectArray<JavaScriptObject> jsArray = JsObjectArray.create();
        for (T t : tList) {
            jsArray.add(toJso(t));
        }
        return jsArray;
    }

    public JsObjectArray<JavaScriptObject> toJsoArray(Collection<? extends JsonBuilder> jsonBuilders) {
        JsObjectArray<JavaScriptObject> jsSuggestions = JsObjectArray.create();
        JavaScriptObject[] jsArray = new JavaScriptObject[jsonBuilders.size()];
        Collections2.transform(jsonBuilders, jsonBuilderToJs).toArray(jsArray);
        jsSuggestions.add(jsArray);
        return jsSuggestions;
    }

    <T> ObjectMapper<T> getMapper(T t) {
        return (ObjectMapper<T>) getMapper(t.getClass());
    }

    <T> ObjectMapper<T> getMapper(Class<T> clazz) {
        return (ObjectMapper<T>) mappers.get(clazz);
    }
}
