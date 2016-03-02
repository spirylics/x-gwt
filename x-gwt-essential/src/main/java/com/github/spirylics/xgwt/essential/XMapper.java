package com.github.spirylics.xgwt.essential;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.query.client.GQ;
import com.google.gwt.query.client.IsProperties;
import com.google.gwt.query.client.builders.JsonBuilder;
import com.google.gwt.query.client.js.JsObjectArray;

import java.util.Collection;
import java.util.Map;

public class XMapper {

    final Map<Class<?>, ObjectMapper<?>> mappers;
    final Map<Class<?>, Function<String, ?>> readers;

    public XMapper(Map<Class<?>, ObjectMapper<?>> mappers) {
        this.mappers = mappers;
        this.readers = new ImmutableMap.Builder<Class<?>, Function<String, ?>>()
                .put(String.class, new Function<String, String>() {
                    public String apply(String input) {
                        return input;
                    }
                }).put(Boolean.class, new Function<String, Boolean>() {
                    public Boolean apply(String input) {
                        return Boolean.parseBoolean(input);
                    }
                }).put(Integer.class, new Function<String, Integer>() {
                    public Integer apply(String input) {
                        return new Integer(input);
                    }
                }).put(Float.class, new Function<String, Float>() {
                    public Float apply(String input) {
                        return new Float(input);
                    }
                }).put(Double.class, new Function<String, Double>() {
                    public Double apply(String input) {
                        return new Double(input);
                    }
                }).put(JSONObject.class, new Function<String, JSONObject>() {
                    public JSONObject apply(String input) {
                        return new JSONObject(JsonUtils.safeEval(input));
                    }
                })
                .build();
    }


    public <V> String write(V value) {
        if (value == null) {
            return null;
        } else if (value instanceof String || value instanceof Number || value instanceof Boolean) {
            return String.valueOf(value);
        } else {
            return getMapper(value).write(value);
        }
    }

    public <V> V read(String input, Class<V> clazz) {
        if (input == null) {
            return null;
        } else if (readers.containsKey(clazz)) {
            return (V) readers.get(clazz).apply(input);
        } else {
            return getMapper(clazz).read(input);
        }
    }

    public <V> V convert(Object object, Class<V> clazz) {
        if (object == null) {
            return null;
        } else if (object.getClass().equals(clazz)) {
            return (V) object;
        } else if (object instanceof String) {
            return read((String) object, clazz);
        } else if (object instanceof Collection) {
            Collection<?> objects = (Collection<?>) object;
            if (JavaScriptObject.class.equals(clazz)) {
                JsObjectArray<JavaScriptObject> jsArray = JsObjectArray.create();
                for (Object o : objects) {
                    jsArray.add(convert(o, JavaScriptObject.class));
                }
                return (V) jsArray;
            }
        } else if (object instanceof JsonBuilder) {
            JsonBuilder jsonBuilder = (JsonBuilder) object;
            if (JavaScriptObject.class.equals(clazz)) {
                return jsonBuilder.getDataImpl();
            } else {
                return convert(jsonBuilder.getDataImpl(), clazz);
            }
        } else if (object instanceof JavaScriptObject) {
            JavaScriptObject jso = (JavaScriptObject) object;
            if (JSONObject.class.equals(clazz)) {
                return (V) new JSONObject(jso);
            } else {
                return read(JsonUtils.stringify(jso), clazz);
            }
        }
        throw new UnsupportedOperationException("Conversion not supported: object=" + String.valueOf(object) + ",clazz=" + String.valueOf(clazz));
    }

    public <V extends JsonBuilder> V convert(JavaScriptObject jso, Class<V> clazz) {
        return GQ.create(clazz, (IsProperties) jso);
    }

    <T> ObjectMapper<T> getMapper(T t) {
        return (ObjectMapper<T>) getMapper(t.getClass());
    }

    <T> ObjectMapper<T> getMapper(Class<T> clazz) {
        return (ObjectMapper<T>) mappers.get(clazz);
    }
}
