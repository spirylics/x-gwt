package com.github.spirylics.xgwt.essential;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageEvent;
import com.google.gwt.storage.client.StorageMap;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class XStorage implements StorageEvent.Handler {
    final XMapper xMapper;
    final Storage storage;
    final StorageMap storageMap;
    final EventBus eventBus;
    final Map<String, Class<?>> storageKeyClass;

    public XStorage(XMapper xMapper, Storage storage, EventBus eventBus) {
        this.xMapper = xMapper;
        this.storage = storage;
        this.storageMap = new StorageMap(storage);
        this.eventBus = eventBus;
        ImmutableMap.Builder<String, Class<?>> storageKeyClassBuilder = new ImmutableMap.Builder<>();
        if (xMapper.mappers != null) {
            for (Class<?> clazz : xMapper.mappers.keySet()) {
                storageKeyClassBuilder.put(clazz.getSimpleName(), clazz);
            }
        }
        this.storageKeyClass = storageKeyClassBuilder.build();
    }

    public <T> XStorage save(String key, T value) {
        storage.setItem(key, xMapper.write(value));
        return this;
    }

    public <T> XStorage save(T value) {
        return save(getKey(value), value);
    }

    public void deleteRaw(String key) {
        storage.removeItem(key);
    }

    public void delete(Class<?> clazz, String id) {
        deleteRaw(getKey(clazz, id));
    }

    public <T> void delete(T value) {
        deleteRaw(getKey(value));
    }

    public <T> boolean contains(final T value) {
        return contains(getKey(value));
    }

    public <T> boolean contains(final Class<T> clazz, String id) {
        return contains(getKey(clazz, id));
    }

    public <T> boolean contains(String key) {
        return key != null && getStorageMap().containsKey(key);
    }

    public <T> T findById(final Class<T> clazz, String id) {
        return xMapper.read(findRawById(clazz, id), clazz);
    }

    public String findRawById(final Class<?> clazz, String id) {
        return storage.getItem(getKey(clazz, id));
    }

    public int count(Class<?> clazz) {
        return findRaw(clazz).size();
    }

    public <T> List<T> find(final Class<T> clazz) {
        return Lists.transform(findRaw(clazz), new Function<String, T>() {
            @Nullable
            @Override
            public T apply(String input) {
                return xMapper.read(input, clazz);
            }
        });
    }

    public <T> List<String> findRaw(final Class<T> clazz) {
        return Ordering.natural().sortedCopy(Maps.filterKeys(getStorageMap(),
                input -> Strings.emptyToNull(input).startsWith(clazz.getSimpleName())).values());
    }

    StorageMap getStorageMap() {
        return this.storageMap;
    }

    <T> String getKey(T value) {
        if (value == null) {
            return null;
        }
        return getKey(value.getClass(), String.valueOf(value.hashCode()));
    }

    String getKey(Class<?> clazz, String id) {
        if (clazz == null || Strings.isNullOrEmpty(id)) {
            return null;
        }
        return clazz.getSimpleName() + "." + id;
    }

    <T> T read(String input, Class<T> clazz) {
        return (T) xMapper.read(input, clazz);
    }

    Class<?> getStorageClass(String key) {
        String simpleClassName = key.substring(0, key.indexOf("."));
        return storageKeyClass.get(simpleClassName);
    }

    @Override
    public void onStorageChange(StorageEvent event) {
        Class clazz = getStorageClass(event.getKey());
        ModelEvent.fire(eventBus, clazz, read(event.getOldValue(), clazz), read(event.getNewValue(), clazz));
    }
}
