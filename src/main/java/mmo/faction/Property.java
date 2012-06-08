package mmo.faction;

import java.util.Map;

public class Property<K,V> implements Map.Entry<K,V> {
	private K key;
	private V value;
	
	public Property(K k) {
		this(k,null);
	}
	public Property(K k, V v) {
		key = k;
		value = v;
	}
	public K getKey() {
		return key;
	}


	public V getValue() {
		return value;
	}


	public V setValue(V value) {
		this.value = value;
		return this.value;
	}

}
