package org.yapframework.metadata;

import org.yapframework.HasAndBelongsToManyProxy;

public class HasAndBelongsToMany extends CollectionRelationship<HasAndBelongsToMany> {
    private String table;
    private String foreignKeyColumn;
    private String orderColumn;
    private HasAndBelongsToManyProxy proxy;

    public HasAndBelongsToMany(String name) {
        super(name);
    }

    public String getTable() {
        return table;
    }

    public HasAndBelongsToMany table(String table) {
        this.table = table;
        return this;
    }

    public String getForeignKeyColumn() {
        return foreignKeyColumn;
    }

    public HasAndBelongsToMany foreignKeyColumn(String foreignKeyColumn) {
        this.foreignKeyColumn = foreignKeyColumn;
        return this;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public HasAndBelongsToMany orderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
        return this;
    }

    public HasAndBelongsToManyProxy<?, ?> getProxy() {
        return proxy;
    }
    public HasAndBelongsToMany proxy(HasAndBelongsToManyProxy<?, ?> proxy) {
        this.proxy = proxy;
        return this;
    }
}
