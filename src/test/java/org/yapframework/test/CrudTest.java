package org.yapframework.test;

import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.yapframework.Model;
import org.yapframework.exceptions.OptimisticLockingException;

import static org.junit.Assert.*;

@DataSet("PersistenceContextTest.xml")
public class CrudTest extends PersistenceContextTest {
    @Test
    public void testUpdateNullBelongsTo() {
        context.find("Contact", 1)
                .set("gender", null)
                .save();

        assertNull(context.find("Contact", 1).getModel("gender"));
    }

    @Test
    public void testInsertSetId() {
        Model m = context.create("Contact")
                .set("first_name", "Joe")
                .save();

        assertNotNull(m.getId());
    }

    @Test
    public void testInsertNull() {
        Model contact = context.create("Contact")
                .set("first_name", "anonymous")
                .set("last_name", null)
                .save();

        contact = context.find("Contact", contact.getId());
        assertNull(contact.get("last_name", String.class));
    }

    @Test
    public void testUpdateNull() {
        Model contact = context.find("Contact", 1);
        assertNotNull(contact.get("last_name", String.class));

        contact.set("last_name", null)
                .save();

        contact = context.find("Contact", 1);
        assertNull(contact.get("last_name", String.class));
    }

    @Test
    public void testDestroyHasMany() {
        Model contact = context.find("Contact", 1);
        contact.getList("phone_numbers").remove(1);
        contact.save();
        assertEquals(1, context.find("Contact", 1).getList("phone_numbers").size());
    }

    @Test
    public void testDestroyHasAndBelongsToMany() {
        Model contact = context.find("Contact", 1);
        contact.getList("groups").remove(1);
        contact.save();
        assertEquals(1, context.find("Contact", 1).getList("groups").size());
    }

    @Test
    public void testDelete() {
        context.delete(context.find("Contact", 1));
        assertNull(context.find("Contact", 1));
    }

    @Test(expected = OptimisticLockingException.class)
    public void testOptimisticLockingException() {
        Model contact = context.find("Contact", 1);
        contact.setVersion(0);
        contact.save();
    }

    @Test
    public void testUpdateVersion() {
        Model contact = context.create("Contact");
        contact.set("first_name", "Bill");
        contact.save();
        assertEquals((Integer) 0, contact.getVersion());
        contact.save();
        assertEquals((Integer) 1, contact.getVersion());
        contact = context.find("Contact", contact.getId());
        assertEquals((Integer) 1, contact.getVersion());
    }
}
