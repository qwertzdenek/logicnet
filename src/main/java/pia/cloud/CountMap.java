package pia.cloud;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CountMap<T> {
	private Map<T, Integer> map;

    /**
     * Creates a new map.
     */
    public CountMap() {
        super();
        map = new HashMap<>();
    }

	/**
	 * Creates a new map.
	 */
	public CountMap(int size) {
		super();
		map = new HashMap<>(size);
	}
	
	/**
	 * Increments a count for a given key.
	 * @param key the key.
	 * @param increment the increment.
	 * @return a new count for a given key.
	 */
	public int inc(T key, int increment) {
		Integer count = map.get(key);
		
		if (count == null) {
			map.put(key, increment);
			return increment;
		} else {
			map.put(key, count + increment);
			return count + increment;
		}
	}

    /**
     * Decrements a count for a given key.
     * @param key the key.
     * @param decrement the decrement.
     * @return a new count for a given key.
     */
    public int dec(T key, int decrement) {
        Integer count = map.get(key);

        if (count == null) {
            return 0;
        } else {
            map.put(key, count - decrement);
            return count + decrement;
        }
    }
	
	/**
	 * Gets count for the given key.
	 * @param key the key.
	 * @return the count for the given key.
	 */
	public int get(T key) {
		Integer count = map.get(key);
		
		if (count == null) {
			return 0;
		} else {
			return count;
		}
	}
	
	/**
	 * Sets a count for a given key.
	 * @param key the key.
	 * @param value the new value.
	 * @return the new value.
	 */
	public int set(T key, int value) {
        map.put(key, value);
		
		return value;
	}
	
	/**
	 * Clears the map.
	 */
	public void clear() {
		map.clear();
	}

    public int size() { return map.size(); }

	/**
	 * Gets the key set.
	 * @return the key set.
	 */
	public Set<T> getKeySet() {
		return map.keySet();
	}

    public Set<Map.Entry<T, Integer>> getEntrySet() { return map.entrySet(); }

	@Override
	public String toString() {
		return map.toString();
	}
}
