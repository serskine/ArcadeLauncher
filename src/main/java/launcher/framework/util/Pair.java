package launcher.framework.util;

import java.util.Map;

public class Pair<K,V> implements Map.Entry<K,V> {
    public K key;
    public V value;

    public Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        return this.value = value;
    }
}
