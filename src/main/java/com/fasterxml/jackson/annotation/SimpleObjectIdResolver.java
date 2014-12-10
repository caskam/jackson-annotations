package com.fasterxml.jackson.annotation;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;

/**
 * Simple implementation of {@link ObjectIdResolver}
 * 
 * @author Pascal Gélinas
 */
public class SimpleObjectIdResolver implements ObjectIdResolver {
    private Map<IdKey,Object> _items;

    @Override
    public void bindItem(IdKey id, Object ob)
    {
        if (_items == null) {
            _items = new HashMap<ObjectIdGenerator.IdKey,Object>();
        } else if (_items.containsKey(id)) {
            throw new IllegalStateException("Already had POJO for id (" + id.key.getClass().getName() + ") [" + id
                    + "]");
        }
        _items.put(id, ob);
    }

    @Override
    public Object resolveId(IdKey id) {
        return (_items == null) ? null : _items.get(id);
    }

    @Override
    public boolean canUseFor(ObjectIdResolver resolverType) {
        return resolverType.getClass() == getClass();
    }
    
    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return this;
    }
}
