package org.yapframework.test;

import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.yapframework.Model;

import java.util.List;

import static org.junit.Assert.*;

@DataSet("PersistenceContextTest.xml")
public class FetchTest extends PersistenceContextTest {
    @Test
    public void testFind() {
        Model contact = context.find("Contact", 1);
        assertEquals("John", contact.get("first_name", String.class));
        assertEquals("Doe", contact.get("last_name", String.class));
    }

    @Test
    public void testFetchHasMany() {
        Model contact = context.find("Contact", 1);
        assertNull(contact.getValues().get("phone_numbers")); // ensure lazy-loaded
        List<Model> phoneNumbers = contact.getList("phone_numbers");
        assertEquals(2, phoneNumbers.size());
        assertEquals("Home", phoneNumbers.get(0).get("type", String.class));
        assertEquals("Mobile", phoneNumbers.get(1).get("type", String.class));
    }

    @Test
    public void testFetchHasAndBelongsToMany() {
        Model contact = context.find("Contact", 1);
        assertNull(contact.getValues().get("groups")); // ensure lazy-loaded
        List<Model> groups = contact.getList("groups");
        assertEquals(2, groups.size());
        assertEquals("Friends", groups.get(0).get("name", String.class));
        assertEquals("Coworkers", groups.get(1).get("name", String.class));
    }

    @Test
    public void testFetchBelongsTo() {
        Model contact = context.find("Contact", 1);
        assertNull(contact.getValues().get("gender")); // ensure lazy-loaded
        assertEquals("Male", contact.getModel("gender").get("name", String.class));
    }

    public void testInsertEmptyValue() {
    }

    public void testUpdateEmptyValues() {
    }

    public void testInvalidPropertySet() {
        // test invalid property with Model or List<Model> value
    }
}
