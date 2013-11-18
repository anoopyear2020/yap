package org.yapframework.test;

import org.junit.Before;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.TestDataSource;
import org.yapframework.PersistenceContext;
import org.yapframework.test.config.PostgresTestConfiguration;

import javax.sql.DataSource;

public abstract class PersistenceContextTest extends UnitilsJUnit4 {
    @TestDataSource private DataSource dataSource;

    protected PersistenceContext context;

    @Before public void setUp() {
        context = PostgresTestConfiguration.configurePersistenceContext(dataSource);
    }
}
