package org.yapframework.metadata;

public class HasAndBelongsToMany extends CollectionRelationship {
    private String table;
    private String foreignKeyColumn;
    private String orderColumn;

    public HasAndBelongsToMany(String name, String column, String relatedToType, String table, String foreignKeyColumn, String orderColumn) {
        super(name, column, relatedToType);
        this.table = table;
        this.foreignKeyColumn = foreignKeyColumn;
        this.orderColumn = orderColumn;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getForeignKeyColumn() {
        return foreignKeyColumn;
    }

    public void setForeignKeyColumn(String foreignKeyColumn) {
        this.foreignKeyColumn = foreignKeyColumn;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }
}
