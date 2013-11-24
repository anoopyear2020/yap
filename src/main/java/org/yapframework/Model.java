package org.yapframework;

import org.yapframework.metadata.ModelType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a record in the database
 */
public class Model {
    private Map<String,Object> values;
    private ModelType type;
    private PersistenceContext context;
    private int order;

    public Model(ModelType type, PersistenceContext context) {
        this.type = type;
        this.context = context;
        values = new HashMap<String,Object>();
    }

    Model(Map<String,Object> values, ModelType type, PersistenceContext context) {
        this.values = values;
        this.type = type;
        this.context = context;
    }

    /**
     * Gets the persistence metadata.
     * @return
     */
    public ModelType getType() {
        return type;
    }

    /**
     * Returns the model's primary key value.
     * @return
     */
    public Object getId() {
        return values.get(type.getPrimaryKey());
    }

    /**
     * Returns the model's primary key value.
     * @param retClass The class of the id field
     * @return
     */
    public <T> T getId(Class<T> retClass) {
        return (T) values.get(type.getPrimaryKey());
    }

    /**
     * Sets the primary key value for this model.
     * @param id
     * @return
     */
    public Model setId(Object id) {
        set(type.getPrimaryKey(), id);
        return this;
    }

    /**
     * Gets the version number used for optimistic locking.
     * @return
     */
    public Integer getVersion() {
        String column = type.getVersionColumn();

        if(column == null) {
            return null;
        } else {
            return get(column, Integer.class);
        }
    }

    /**
     * Sets the version number used for optimistic locking.
     * @param version
     * @return
     */
    public Model setVersion(Integer version) {
        String column = type.getVersionColumn();

        if(column == null) {
            throw new RuntimeException("Versioning is not configured for model type " + type.getName());
        } else {
            set(column, version);
        }

        return this;
    }

    /**
     * Gets a field's value.
     * @param fieldName
     * @param retClass
     * @param <T>
     * @return
     */
    public <T> T get(String fieldName, Class<T> retClass) {
        PropertyProxy<?, T> proxy = (PropertyProxy<?, T>) type.proxyForProperty(fieldName);

        if(proxy != null) {
            return proxy.get(this);
        }

        if(values.containsKey(fieldName)) {
            return (T) values.get(fieldName);
        } else  {
            T value = context.fetch(this, fieldName, retClass);
            values.put(fieldName, value);
            return value;
        }
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
     * Convenience method to get a proxied collection property
     * @param fieldName The name of the relationship
     * @param itemType The type of each item in the collection
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String fieldName, Class<T> itemType) {
        return (List<T>) get(fieldName, List.class);
    }

    /**
     * Sets a field's value.
     * @param fieldName
     * @param value
     * @return the model instance
     */
    public Model set(String fieldName, Object value) {
        PropertyProxy<Object,?> proxy = (PropertyProxy<Object, ?>) type.proxyForProperty(fieldName);

        if(proxy != null) {
            proxy.set(this, value);
        } else {
            values.put(fieldName, value);
        }

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
            String type = this.type.getName();

            if(id == null || type == null) {
                return super.equals(obj);
            } else {
                Model model = (Model) obj;

                if(model.type != null) {
                    return id.equals(model.getId()) && type.equals(model.type.getName());
                }
            }
        }

        return false;
    }
}
