package org.yapframework.metadata;

/**
 * A relationship that yields a collection.  This class is primarily used to distinguish relationships stored in
 * the owner model table from those stored in a related table.
 */
public class CollectionRelationship extends Relationship {
    public CollectionRelationship(String name, String column, String relatedToType) {
        super(name, column, relatedToType);
    }
}
