/**
 * Copyright (C) cedarsoft GmbH.
 *
 * Licensed under the GNU General Public License version 3 (the "License")
 * with Classpath Exception; you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *         http://www.cedarsoft.org/gpl3ce
 *         (GPL 3 with Classpath Exception)
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation. cedarsoft GmbH designates this
 * particular file as subject to the "Classpath" exception as provided
 * by cedarsoft GmbH in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact cedarsoft GmbH, 72810 Gomaringen, Germany,
 * or visit www.cedarsoft.com if you need additional information or
 * have any questions.
 */

package de.xore.util.persistence.hibernate;

import de.xore.util.persistence.DatabaseConfiguration;
import de.xore.util.persistence.DatabaseType;
import junit.framework.TestCase;
import org.hibernate.Session;

/**
 * <p>
 * Date: 21.06.2006<br>
 * Time: 22:07:43<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class SimpleHibernateTest extends TestCase {
  public void testDummy() {

  }

  public void _testDBConnector() {
    DatabaseConfiguration configuration = new DatabaseConfiguration( "url", "user", "pass", DatabaseType.MYSQL );
    HibernateDatabaseConnector connector = new HibernateDatabaseConnector( configuration );
    assertNotNull( connector );

    connector.dispose();
  }

  public void _testHibernateQueryManager() {
    DatabaseConfiguration configuration = new DatabaseConfiguration( "url", "user", "pass", DatabaseType.MYSQL );
    HibernateQueryManager queryManager = new HibernateQueryManager( new HibernateDatabaseConnector( configuration ) );
    assertNotNull( queryManager );

    assertFalse( queryManager.getConnector().isSessionActive() );
    queryManager.getConnector().closeSession();

    queryManager.dispose();
  }

  public void _testConnectToDB() {
    DatabaseConfiguration configuration = DatabaseConfiguration.createConfiguration( "hibernate" );
    assertNotNull( configuration );
    assertEquals( DatabaseType.MYSQL, configuration.getDatabaseType() );
    HibernateDatabaseConnector databaseConnector = new HibernateDatabaseConnector( configuration );

    Session session = databaseConnector.openSession();
    assertNotNull( session );
    databaseConnector.closeSession();

    databaseConnector.recreateDb();
    databaseConnector.closeSession();
    databaseConnector.dispose();
  }

  public void _testAnnotatedClass() {
    DatabaseConfiguration configuration = DatabaseConfiguration.createConfiguration( "hibernate" );
    HibernateDatabaseConnector databaseConnector = new HibernateDatabaseConnector( configuration );

    databaseConnector.addAnnotadedClass( UserMock.class );
    databaseConnector.recreateDb();

    UserMock userMock = new UserMock();
    databaseConnector.beginTransaction();
    databaseConnector.save( userMock );
    databaseConnector.commit();
    String id = userMock.getId();
    assertTrue( id != null && id.length() > 0 );

    assertNotNull( databaseConnector.load( UserMock.class, id ) );

    databaseConnector.dispose();
  }
}
