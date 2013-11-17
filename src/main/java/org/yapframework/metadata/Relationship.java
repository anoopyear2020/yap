package org.yapframework.metadata;

public abstract class Relationship {
    private String name;
    private String column;
    private String relatedToType;

    public Relationship(String name, String column, String relatedToType) {
        this.name = name;
        this.column = column;
        this.relatedToType = relatedToType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getRelatedToType() {
        return relatedToType;
    }

    public void setRelatedToType(String relatedToType) {
        this.relatedToType = relatedToType;
    }
}
