package org.yapframework.test.config;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.jooq.SQLDialect;
import org.yapframework.Model;
import org.yapframework.PersistenceContext;
import org.yapframework.PropertyProxy;
import org.yapframework.metadata.BelongsTo;
import org.yapframework.metadata.HasAndBelongsToMany;
import org.yapframework.metadata.HasMany;
import org.yapframework.metadata.ModelType;

import javax.sql.DataSource;

public class PostgresTestConfiguration {
    public static PersistenceContext configurePersistenceContext() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return configurePersistenceContext(configureDataSource());
    }

    public static PersistenceContext configurePersistenceContext(DataSource ds) {
        try {
            PersistenceContext ctx = new PersistenceContext()
                    .setDataSource(ds)
                    .configure(createContactModelType())
                    .configure(createPhoneNumberModelType())
                    .configure(createGroupModelType())
                    .configure(createGenderModelType())
                    .setDialect(SQLDialect.POSTGRES)
                    .init();

            return ctx;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ModelType createGenderModelType() {
        return new ModelType("Gender")
                .table("genders");
    }

    private static ModelType createContactModelType() {
        return new ModelType("Contact")
                .table("contacts")
                .versionColumn("version")
                .proxyProperty("yap", createTestProxy())
                .relationship(new HasMany("phone_numbers").type("PhoneNumber").column("contact_id").orderColumn("position").deleteOrphans(true))
                .relationship(new BelongsTo("gender").type("Gender").column("gender_id"))
                .relationship(new HasAndBelongsToMany("groups").type("Group").column("group_id").table("contacts_groups").foreignKeyColumn("contact_id").orderColumn("position"));
    }

    private static PropertyProxy<String,String> createTestProxy() {
        return new PropertyProxy<String, String>() {
            public String get(Model model) {
                return "yap yap yap";
            }

            public void set(Model model, String value) {
                model.getValues().put("yap", "yap yap yap");
            }
        };
    }

    private static ModelType createPhoneNumberModelType() {
        return new ModelType("PhoneNumber")
                .table("phone_numbers")
                .relationship(new BelongsTo("contact").type("Contact").column("contact_id"));
    }

    private static ModelType createGroupModelType() {
        return new ModelType("Group")
                .table("groups");
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
