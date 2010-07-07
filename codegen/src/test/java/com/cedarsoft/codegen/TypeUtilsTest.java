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

package com.cedarsoft.codegen;

import com.cedarsoft.codegen.mock.ClassTypeMock;
import com.cedarsoft.codegen.mock.CollectionTypeMirrorMock;
import com.cedarsoft.codegen.mock.TypesMock;
import org.testng.annotations.*;

import java.util.List;
import java.util.Set;

import static org.testng.Assert.*;

/**
 *
 */
public class TypeUtilsTest {
  @BeforeMethod
  public void setup() {
    TypeUtils.setTypes( new TypesMock() );
  }

  @Test
  public void testCollParam() {
    assertEquals( TypeUtils.getCollectionParam( new CollectionTypeMirrorMock( List.class, String.class ) ).toString(), "java.lang.String" );
    assertEquals( TypeUtils.getCollectionParam( new CollectionTypeMirrorMock( Set.class, String.class ) ).toString(), "java.lang.String" );
    assertEquals( TypeUtils.getCollectionParam( new CollectionTypeMirrorMock( List.class, Integer.class ) ).toString(), "java.lang.Integer" );
  }

  @Test
  public void testPrimitiveType() {
    assertTrue( TypeUtils.isSimpleType( new ClassTypeMock( Integer.TYPE ) ) );
    assertTrue( TypeUtils.isSimpleType( new ClassTypeMock( Integer.class ) ) );
    assertTrue( TypeUtils.isSimpleType( new ClassTypeMock( String.class ) ) );
    assertTrue( TypeUtils.isSimpleType( new ClassTypeMock( Character.class ) ) );

    assertFalse( TypeUtils.isSimpleType( new ClassTypeMock( Object.class ) ) );
    assertFalse( TypeUtils.isSimpleType( new ClassTypeMock( List.class ) ) );
  }
}