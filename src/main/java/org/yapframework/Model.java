package org.yapframework;

import org.yapframework.metadata.ModelMetaData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a record in the database
 */
public class Model {
    private Map<String,Object> values;
    private ModelMetaData metaData;
    private PersistenceContext context;
    private int order;
    private boolean markedForDestruction;

    public Model(ModelMetaData metaData, PersistenceContext context) {
        this.metaData = metaData;
        this.context = context;
        values = new HashMap<String,Object>();
    }

    Model(Map<String,Object> values, ModelMetaData metaData, PersistenceContext context) {
        this.values = values;
        this.metaData = metaData;
        this.context = context;
    }

    /**
     * Gets the persistence metadata.
     * @return
     */
    public ModelMetaData getMetaData() {
        return metaData;
    }

    /**
     * Returns the model's primary key value.
     * @return
     */
    public Object getId() {
        return values.get(metaData.getPrimaryKey());
    }

    /**
     * Returns the model's primary key value.
     * @param retClass The class of the id field
     * @return
     */
    public <T> T getId(Class<T> retClass) {
        return (T) values.get(metaData.getPrimaryKey());
    }

    /**
     * Gets a field's value.
     * @param fieldName
     * @param retClass
     * @param <T>
     * @return
     */
    public <T> T get(String fieldName, Class<T> retClass) {
        T value = (T) values.get(fieldName);

        if(!values.containsKey(fieldName)) {
            value = context.fetch(this, fieldName, retClass);
            values.put(fieldName, value);
        }

        return value;
    }

    /**
     * Convenience method to get the value of a belongsTo relationship
     * @param fieldName The name of the relationship
     * @return
     */
    public Model getModel(String fieldName) {
        return get(fieldName, Model.class);
    }

    /**
     * Convenience method to get a collection property
     * @param fieldName The name of the relationship
     * @return
     */
    public List<Model> getList(String fieldName) {
        return get(fieldName, List.class);
    }

    /**
     * Sets a field's value.
     * @param fieldName
     * @param value
     * @return the model instance
     */
    public Model set(String fieldName, Object value) {
        values.put(fieldName, value);
        return this;
    }

    /**
     * Gets all field values as a map.
     * @return
     */
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * If this item is a hasMany relationship, this is order that item appears in the collection.
     * @return
     */
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * Persists any changes to the model.
     */
    public Model save() {
        context.save(this);
        return this;
    }

    /**
     * The hash code is based on the id
     * @return
     */
    public int hashCode() {
        Object id = getId();
        return id == null ? super.hashCode() : id.hashCode();
    }

    /**
     * Returns true if the specified object is the same instance or a model with the same type and id
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if(obj instanceof Model) {
            Object id = getId();
            String type = metaData.getType();

            if(id == null || type == null) {
                return super.equals(obj);
            } else {
                Model model = (Model) obj;

                if(model.metaData != null) {
                    return id.equals(model.getId()) && type.equals(model.metaData.getType());
                }
            }
        }

        return false;
    }
}
