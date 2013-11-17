package org.yapframework.metadata;

public abstract class Relationship<T> {
    private String name;
    private String column;
    private String relatedToType;

    public Relationship(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getColumn() {
        return column;
    }
    public T column(String column) {
        this.column = column;
        return (T) this;
    }

    public String getType() {
        return relatedToType;
    }
    public T type(String relatedToType) {
        this.relatedToType = relatedToType;
        return (T) this;
    }
}
