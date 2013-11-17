package org.yapframework.metadata;

public class BelongsTo extends Relationship {
    public BelongsTo(String name, String column, String relatedToType) {
        super(name, column, relatedToType);
    }
}
