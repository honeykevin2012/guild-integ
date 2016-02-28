package com.game.common.basics.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public final class Enumerator implements Enumeration<Object> {

	private Iterator<?> iterator;

	public Enumerator(Collection<?> collection) {
		this(collection.iterator());
	}

	public Enumerator(Collection<?> collection, boolean clone) {
		this(collection.iterator(), clone);
	}

	public Enumerator(Iterator<?> iterator) {
		this.iterator = null;
		this.iterator = iterator;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Enumerator(Iterator<?> iterator, boolean clone) {
		this.iterator = null;
		if (!clone) {
			this.iterator = iterator;
		} else {
			List list = new ArrayList();
			while(iterator.hasNext())
				list.add(iterator.next());
			this.iterator = list.iterator();
		}
	}

	public Enumerator(Map<?, ?> map) {
		this(map.values().iterator());
	}

	public Enumerator(Map<?, ?> map, boolean clone) {
		this(map.values().iterator(), clone);
	}

	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	public Object nextElement() throws NoSuchElementException {
		return iterator.next();
	}

}
