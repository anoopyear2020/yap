package org.yapframework.metadata;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about how a model is persisted and related to other models.
 */
public class ModelType {
    private String name;
    private String table;
    private String primaryKey = "id";
    private Map<String,Relationship> relationships = new HashMap<String, Relationship>();

    public ModelType(String name) {
        this.name = name;
    }

    /**
     * Gets the unique name used to identify this model type.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the table name
     * @return
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets the table name.
     * @param table
     * @return
     */
    public ModelType table(String table) {
        this.table = table;
        return this;
    }

    /**
     * Gets the primary key column name
     * @return
     */
    public String getPrimaryKey() {
        return primaryKey;
    }

    /**
     * Sets the primary key column name. Defaults to "id"
     * @param primaryKey
     * @return
     */
    public ModelType primaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    /**
     * Adds a relationship to another model.
     * @param rel
     * @return
     */
    public ModelType relationship(Relationship rel) {
        relationships.put(rel.getName(), rel);
        return this;
    }

    /**
     * Looks up a relationship by name
     * @param name
     * @return
     */
    public Relationship relationshipFor(String name) {
        return relationships.get(name);
    }

    /**
     * Gets a map of all relationships
     * @return
     */
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
