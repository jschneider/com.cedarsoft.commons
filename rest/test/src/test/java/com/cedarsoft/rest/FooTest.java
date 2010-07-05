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

package com.cedarsoft.rest;

import com.cedarsoft.jaxb.AbstractJaxbObject;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

/**
 *
 */
public class FooTest extends AbstractJaxbTest<FooTest.Foo> {
  @NotNull
  @Override
  protected Class<Foo> getJaxbType() {
    return Foo.class;
  }

  @NotNull
  @Override
  protected String expectedXml() {
    return "<ns2:foo xmlns:ns2=\"test:foo\" href=\"my:uri\">\n" +
      "  <id>daId</id>\n" +
      "  <daValue>daValueA</daValue>\n" +
      "</ns2:foo>";
  }

  @NotNull
  @Override
  protected Foo createObjectToSerialize() throws Exception {
    Foo foo = new Foo();
    foo.setDaValue( "daValueA" );
    foo.setHref( new URI( "my:uri" ) );
    foo.setId( "daId" );
    return foo;
  }

  @XmlRootElement( namespace = "test:foo" )
  public static class Foo extends AbstractJaxbObject {
    private String daValue = "default";

    public String getDaValue() {
      return daValue;
    }

    public void setDaValue( String daValue ) {
      this.daValue = daValue;
    }
  }
}