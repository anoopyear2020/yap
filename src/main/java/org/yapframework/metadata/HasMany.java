package org.yapframework.metadata;

public class HasMany extends CollectionRelationship<HasMany> {
    private boolean destroyOrphans = true;
    private String orderColumn;

    public HasMany(String name) {
        super(name);
    }

    public String getOrderColumn() {
        return orderColumn;
    }
    public HasMany orderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
        return this;
    }

    public boolean isDeleteOrphans() {
        return destroyOrphans;
    }
    public HasMany deleteOrphans(boolean destroyOrphans) {
        this.destroyOrphans = destroyOrphans;
        return this;
    }
}
