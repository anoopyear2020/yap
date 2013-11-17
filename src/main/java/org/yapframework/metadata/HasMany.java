package org.yapframework.metadata;

public class HasMany extends CollectionRelationship {
    private boolean destroyOrphans;
    private String orderColumn;

    public HasMany(String name, String column, String relatedToType, boolean destroyDependant, String orderColumn) {
        super(name, column, relatedToType);
        this.destroyOrphans = destroyDependant;
        this.orderColumn = orderColumn;
    }

    public String getOrderColumn() {
        return orderColumn;
    }
    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public boolean isDestroyOrphans() {
        return destroyOrphans;
    }
    public void setDestroyOrphans(boolean destroyOrphans) {
        this.destroyOrphans = destroyOrphans;
    }
}
