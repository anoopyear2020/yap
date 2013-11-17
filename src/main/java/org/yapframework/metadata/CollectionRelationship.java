package org.yapframework.metadata;

/**
 * A relationship that yields a collection.  This class is primarily used to distinguish relationship stored in
 * the owner model table from those stored in a related table.
 */
public class CollectionRelationship<T> extends Relationship<T> {
    public CollectionRelationship(String name) {
        super(name);
    }
}
