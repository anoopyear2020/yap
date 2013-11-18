package org.yapframework.test;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.yapframework.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@DataSet("PersistenceContextTest.xml")
public class QueryTest extends PersistenceContextTest {
    @Test public void testFindBy() {
        Model model = context.findBy("Contact", "first_name", "Jill");
        assertEquals("Jill", model.get("first_name", String.class));
    }

    @Test public void testFindAllByField() {
        List<Model> models = context.findAllBy("Contact", "first_name", "John");
        assertEquals(2, models.size());
    }

    @Test public void testList() {
        List<Model> models = context.list("Contact");
        assertEquals(3, models.size());
    }

    @Test public void testListWithOrder() {
        List<Model> models = context.list("Contact", "first_name", false);
        assertEquals("John", models.get(0).get("first_name", String.class));
    }

    @Test public void testFindAllByMap() {
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("first_name", "John");
        values.put("last_name", "Smith");
        List<Model> models = context.findAllBy("Contact", values);
        assertEquals(1, models.size());
        assertEquals("John", models.get(0).get("first_name", String.class));
        assertEquals("Smith", models.get(0).get("last_name", String.class));
    }

    @Test public void testJooqQuery() {
        SelectJoinStep<Record> query = context.createJooqQuery("Contact");
        Result<Record> result = query.where(DSL.field("first_name").equal("John")).fetch();
        List<Model> models = context.fromJooqResult("Contact", result);
        assertEquals(2, models.size());
    }
}
