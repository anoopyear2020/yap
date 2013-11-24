package org.yapframework.test;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.yapframework.HasAndBelongsToManyProxy;
import org.yapframework.Model;
import org.yapframework.metadata.HasAndBelongsToMany;
import org.yapframework.metadata.ModelType;

import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

@DataSet("PersistenceContextTest.xml")
public class ProxyTest extends PersistenceContextTest {
    private static class Project {
        public Integer id;
        public Project(Integer id) {
            this.id = id;
        }
    }

    private HasAndBelongsToManyProxy<Integer,Project> proxy;

    @Before
    public void configureProxy() {
        proxy = EasyMock.createMock(HasAndBelongsToManyProxy.class);

        ModelType type = context.metaDataFor("Contact");
        type.relationship(new HasAndBelongsToMany("projects")
                .table("contacts_projects")
                .proxy(proxy)
                .column("project_id")
                .foreignKeyColumn("contact_id")
                .orderColumn("position"));
    }

    @Test
    public void testFetchHasAndBelongsToMany() {
        expect(proxy.fetch(1)).andReturn(new Project(1));
        expect(proxy.fetch(2)).andReturn(new Project(2));
        replay(proxy);

        Model contact = context.find("Contact", 1);
        List<Project> projects = contact.getList("projects", Project.class);

        verify(proxy);
        assertEquals((Integer) 1, projects.get(0).id);
        assertEquals((Integer) 2, projects.get(1).id);
    }

    @Test
    public void testRemoveHasAndBelongsToMany() {
        Project p1 = new Project(1), p2 = new Project(2);

        expect(proxy.fetch(1)).andReturn(p1).atLeastOnce();
        expect(proxy.fetch(2)).andReturn(p2).atLeastOnce();
        expect(proxy.idFor(p1)).andReturn(p1.id);
        replay(proxy);

        Model contact = context.find("Contact", 1);
        List<Project> projects = contact.getList("projects", Project.class);
        projects.remove(1);

        contact.save();

        verify(proxy);
        assertEquals(1, context.find("Contact", 1).getList("projects", Project.class).size());
    }

    @Test
    public void testAddHasAndBelongsToMany() {
        Project p1 = new Project(1), p2 = new Project(2), p3 = new Project(3);

        expect(proxy.fetch(1)).andReturn(p1).atLeastOnce();
        expect(proxy.fetch(2)).andReturn(p2).atLeastOnce();
        expect(proxy.idFor(p1)).andReturn(p1.id);
        expect(proxy.idFor(p2)).andReturn(p2.id);
        expect(proxy.idFor(p3)).andReturn(p3.id);
        expect(proxy.fetch(3)).andReturn(p3).anyTimes();
        replay(proxy);

        Model contact = context.find("Contact", 1);
        List<Project> projects = contact.getList("projects", Project.class);
        projects.add(p3);

        contact.save();

        verify(proxy);
        projects = context.find("Contact", 1).getList("projects", Project.class);
        assertEquals(3, projects.size());
        assertEquals(p3, projects.get(2));
    }

    @Test
    public void testProxyGetter() {
        Model contact = context.find("Contact", 1);
        assertEquals("yap yap yap", contact.get("yap", String.class));
    }

    @Test
    public void testProxySetter() {
        Model contact = context.find("Contact", 1);
        contact.set("yap", "ringadingding");
        assertEquals("yap yap yap", contact.getValues().get("yap"));
    }
}
