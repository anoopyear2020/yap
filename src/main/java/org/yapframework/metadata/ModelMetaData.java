package org.yapframework.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about how a model is persisted and related to other models.
 */
public class ModelMetaData {
    private String type;
    private String table;
    private String primaryKey = "id";
    private Map<String,Relationship> relationships = new HashMap<String, Relationship>();

    public String getType() {
        return type;
    }

    public ModelMetaData setType(String type) {
        this.type = type;
        return this;
    }

    public String getTable() {
        return table;
    }

    public ModelMetaData setTable(String table) {
        this.table = table;
        return this;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public ModelMetaData setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public ModelMetaData addRelationship(Relationship rel) {
        relationships.put(rel.getName(), rel);
        return this;
    }

    public Relationship relationshipFor(String name) {
        return relationships.get(name);
    }

    public Map<String, Relationship> getRelationships() {
        return relationships;
    }

    /**
     * Returns true if the specified field is a relationship field such as a hasMany or belongsTo
     * @param field
     * @return
     */
    public boolean hasRelationshipFor(String field) {
        return relationships.containsKey(field);
    }
}
