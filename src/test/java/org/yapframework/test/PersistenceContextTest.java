package org.yapframework.test;

import org.junit.Before;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;
import org.unitils.dbunit.annotation.DataSet;
import org.yapframework.Model;
import org.yapframework.PersistenceContext;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@DataSet
public class PersistenceContextTest extends UnitilsJUnit4 {
    @TestDataSource private DataSource dataSource;

    private PersistenceContext context;

    @Before public void setUp() {
        context = PostgresTestConfiguration.configurePersistenceContext(dataSource);
    }

    @Test public void testFind() {
        Model contact = context.find("contact", 1);
        assertEquals("John", contact.get("first_name", String.class));
        assertEquals("Doe", contact.get("last_name", String.class));
    }

    @Test public void testFetchHasMany() {
        Model contact = context.find("contact", 1);
        assertNull(contact.getValues().get("phone_numbers")); // ensure lazy-loaded
        List<Model> phoneNumbers = contact.getList("phone_numbers");
        assertEquals(2, phoneNumbers.size());
        assertEquals("Home", phoneNumbers.get(0).get("type", String.class));
        assertEquals("Mobile", phoneNumbers.get(1).get("type", String.class));
    }

    @Test public void testFetchHasAndBelongsToMany() {
        Model contact = context.find("contact", 1);
        assertNull(contact.getValues().get("groups")); // ensure lazy-loaded
        List<Model> groups = contact.getList("groups");
        assertEquals(2, groups.size());
        assertEquals("Friends", groups.get(0).get("name", String.class));
        assertEquals("Coworkers", groups.get(1).get("name", String.class));
    }

    @Test public void testFetchBelongsTo() {
        Model contact = context.find("contact", 1);
        assertNull(contact.getValues().get("gender")); // ensure lazy-loaded
        assertEquals("Male", contact.getModel("gender").get("name", String.class));
    }

    @Test public void testUpdateNullBelongsTo() {
        context.find("contact", 1)
                .set("gender", null)
                .save();

        assertNull(context.find("contact", 1).getModel("gender"));
    }

    @Test public void testInsertSetId() {
        Model m = context.create("contact")
                .set("first_name", "Joe")
                .save();

        assertNotNull(m.getId());
    }

    @Test public void testInsertNull() {
        Model contact = context.create("contact")
                .set("first_name", "anonymous")
                .set("last_name", null)
                .save();

        contact = context.find("contact", contact.getId());
        assertNull(contact.get("last_name", String.class));
    }

    @Test public void testUpdateNull() {
        Model contact = context.find("contact", 1);
        assertNotNull(contact.get("last_name", String.class));

        contact.set("last_name", null)
                .save();

        contact = context.find("contact", 1);
        assertNull(contact.get("last_name", String.class));
    }

    @Test public void testDestroyHasMany() {
        Model contact = context.find("contact", 1);
        contact.getList("phone_numbers").remove(1);
        contact.save();
        assertEquals(1, context.find("contact", 1).getList("phone_numbers").size());
    }

    @Test public void testDestroyHasAndBelongsToMany() {
        Model contact = context.find("contact", 1);
        contact.getList("groups").remove(1);
        contact.save();
        assertEquals(1, context.find("contact", 1).getList("groups").size());
    }

    @Test public void testDelete() {
        context.delete(context.find("contact", 1));
        assertNull(context.find("contact", 1));
    }

    public void testInsertEmptyValue() {
    }

    public void testUpdateEmptyValues() {
    }

    public void testInvalidPropertySet() {
        // test invalid property with Model or List<Model> value
    }
}
