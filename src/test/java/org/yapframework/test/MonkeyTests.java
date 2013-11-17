package org.yapframework.test;

import org.apache.log4j.Logger;
import org.yapframework.Model;
import org.yapframework.PersistenceContext;
import org.yapframework.metadata.ModelMetaData;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MonkeyTests {
    private static final Logger LOG = Logger.getLogger(MonkeyTests.class);
    private static PersistenceContext ctx;

    public static void main(String[] args) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ctx = PostgresTestConfiguration.configurePersistenceContext();
//        testFind();
//        testUpdate();
        testInsert();
    }

    private static void testFind() {
        Model contact = ctx.find("contact", 1);
        dump(contact);
    }

    private static void testUpdate() {
        Model contact = ctx.find("contact", 1);
        contact.set("first_name", System.currentTimeMillis());
        ctx.save(contact);
    }

    private static void testInsert() {
        Model newContact = new Model(ctx.metaDataFor("contact"), ctx);

        // groups
        List<Model> groups = new LinkedList<Model>();
        groups.add(ctx.find("group", 1));
        groups.add(ctx.find("group", 2));

        // phone numbers
        ModelMetaData phoneNumberMd = ctx.metaDataFor("phone_number");
        List<Model> phoneNumbers = new LinkedList<Model>();
        phoneNumbers.add(new Model(phoneNumberMd, ctx).set("number", "973-729-6485").set("type", "Home"));
        phoneNumbers.add(new Model(phoneNumberMd, ctx).set("number", "410-940-3959").set("type", "Mobile"));

        newContact.set("first_name", "Mark")
                .set("last_name", "Brocato")
                .set("email", null)
                .set("groups", groups)
                .set("phone_numbers", phoneNumbers);

        ctx.save(newContact);
        LOG.info("Saved contact " + newContact.get("id", Integer.class));

        // remove a phone number
        phoneNumbers.remove(1);
        groups.remove(1);
        ctx.save(newContact);

        // find the saved model
        newContact = ctx.find("contact", newContact.get("id", Integer.class));

        phoneNumbers = newContact.get("phone_numbers", List.class);
        LOG.info("Removed phoneNumber, " + phoneNumbers.size() + " remaining.");

        groups = newContact.get("groups", List.class);
        LOG.info("Removed group, " + groups.size() + " remaining.");
    }

    private static void dump(Model contact) {
        System.out.println(contact.get("first_name", String.class));

        List<Model> phoneNumbers = contact.get("phone_numbers", List.class);

        for(Model phoneNumber:phoneNumbers) {
            LOG.info(phoneNumber.get("number", String.class) + " (" + phoneNumber.get("type", String.class) + ")");
            Model contact2 = phoneNumber.get("contact", Model.class);
            LOG.info(contact2 == contact);
        }
    }
}
