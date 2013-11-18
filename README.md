Yap
===
**Problem:** You want to use Hibernate but can't because your application allows users to customize the data model.

**Solution:** Yap is a runtime (re)configurable orm for Java that doesn't require compiled model classes or static mappings.

Using
-----
Create your tables using SQL:
```sql
CREATE TABLE contacts (
    id serial PRIMARY KEY,
    first_name varchar(50),
    last_name varchar(50)
);

CREATE TABLE phone_numbers (
    id serial PRIMARY KEY,
    contact_id integer,
    type varchar(10),
    number varchar(20),
    position integer
);
```

Configure a yap PersistenceContext:
```java
ModelType contact = new ModelType("Contact")
        .table("contacts")
        .relationship(new HasMany("phone_numbers")
            .type("PhoneNumber")
            .column("contact_id")
            .orderColumn("position"));

ModelType phoneNumber = new ModelType("PhoneNumber")
        .table("phone_numbers")

PersistenceContext ctx = new PersistenceContext()
        .setDataSource(myDataSource)
        .configure(contact)
        .configure(phoneNumber)
        .setDialect(SQLDialect.POSTGRES)
        .init();
```

Save a new model instance:
```java
Model newContact = context.create("Contact");
newContact.set("first_name", "Jane");
newContact.save();
Integer id = newContact.getId();
```

Find a model instance:
```java
Model contact = ctx.find("Contact", id);
String firstName = contact.get("first_name", String.class); // get simple property
List<Model> phoneNumbers = contact.getList("phoneNumbers"); // get relationship property, lazy-loaded!
```

Make a change:
```java
contact.set("first_name", "Joe");
contact.save();
```

Building Yap
------------
Yap is a standard Maven project.  Yap's unit tests require a Postgres database, therefore I recommend running:

    mvn clean install -DskipTests

Running the Unit Tests
----------------------

If you want to run the unit tests, you need to create a database then run liquibase to create the test schema:

    mvn liquibase:update
    mvn test

See liquibase.properties and unitils.properties for the database configuration

Database Support
----------------
Yap is currently under development and only supports Postgres.  Since Yap is built on top of [jooq](http://www.jooq.org), we expect to support the following platforms soon:

 * Postgres
 * SQL Server
 * MySQL
 * Oracle

Limitations
-----------
Currently, yap does not:

 * manage your schema for you.  You are responsible for creating your own tables.
 * persist arrays or collections of non-model objects, such as List<String>
 * support versioning (optimistic locking), though this is coming soon.

About the Name
--------------
What does the fox say?  Yap is an acronym for "Yet another persister".

Licensing
---------
Copyright (c) 2013 Mark Brocato

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
