package org.yapframework;

/**
 * A proxy for fetching and saving items in a HasAndBelongsToMany relationship
 * @param <K> The type of each item's id, typically Integerfor numeric ids and Stringfor guids
 * @param <T> The type of each item in the collection
 */
public interface HasAndBelongsToManyProxy<K,T> {
    public T fetch(K id);
    public Object idFor(T item);
}
