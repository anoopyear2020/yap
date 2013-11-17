package org.yapframework.test;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.jooq.SQLDialect;
import org.yapframework.PersistenceContext;
import org.yapframework.metadata.BelongsTo;
import org.yapframework.metadata.HasAndBelongsToMany;
import org.yapframework.metadata.HasMany;
import org.yapframework.metadata.ModelMetaData;

import javax.sql.DataSource;

public class PostgresTestConfiguration {
    public static PersistenceContext configurePersistenceContext() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return configurePersistenceContext(configureDataSource());
    }

    public static PersistenceContext configurePersistenceContext(DataSource ds) {
        try {
            PersistenceContext ctx = new PersistenceContext()
                    .setDataSource(ds)
                    .configure(createContactModelMetaData())
                    .configure(createPhoneNumberModelMetaData())
                    .configure(createGroupsMetaData())
                    .configure(createGenderModelMetaData())
                    .setDialect(SQLDialect.POSTGRES)
                    .init();

            return ctx;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ModelMetaData createGenderModelMetaData() {
        return new ModelMetaData()
                .setTable("genders")
                .setType("gender");
    }

    private static ModelMetaData createContactModelMetaData() {
        return new ModelMetaData()
                .setTable("contacts")
                .setType("contact")
                .addRelationship(new BelongsTo("gender", "gender_id", "gender"))
                .addRelationship(new HasMany("phone_numbers", "contact_id", "phone_number", true, "position"))
                .addRelationship(new HasAndBelongsToMany("groups", "group_id", "group", "contacts_groups", "contact_id", "position"));
    }

    private static ModelMetaData createPhoneNumberModelMetaData() {
        return new ModelMetaData()
                .setTable("phone_numbers")
                .setType("phone_number")
                .addRelationship(new BelongsTo("contact", "contact_id", "contact"));
    }

    private static ModelMetaData createGroupsMetaData() {
        return new ModelMetaData()
                .setTable("groups")
                .setType("group");
    }

    private static DataSource configureDataSource() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String username = "orm";
        String password = "orm";
        String url = "jdbc:postgresql://localhost/orm";
        Class.forName("org.postgresql.Driver").newInstance();

        ObjectPool connectionPool = new GenericObjectPool(null);
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(url, username, password);
        new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
        return new PoolingDataSource(connectionPool);
    }
}
