package com.cedarsoft.lookup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

/**
 *
 */
public class LazyDynamicLookupTest  {
  private LazyLookup<String> lookup;

  private boolean called;

  @BeforeMethod
  protected void setUp() throws Exception {
    called = false;
    lookup = new LazyLookup<String>() {
      @Override
      public Class<? extends String> getType() {
        return String.class;
      }

      @Override
      @NotNull
      protected String createInstance() {
        if ( called ) {
          throw new IllegalStateException( "has still been called" );
        }
        called = true;
        return "asdf";
      }
    };
  }

  @Test
  public void testLazy() {
    assertFalse( called );
    Map<Class<?>, Object> lookups = lookup.lookups();
    assertTrue( lookups.keySet().contains( String.class ) );
    assertTrue( lookups.keySet().contains( Serializable.class ) );
    assertTrue( lookups.keySet().contains( Comparable.class ) );
    assertTrue( lookups.keySet().contains( CharSequence.class ) );
    assertEquals( 4, lookups.size() );

    assertFalse( called );
    assertEquals( "asdf", lookup.getValue() );
    assertTrue( called );
    assertEquals( "asdf", lookup.lookup( String.class ) );
  }

  @Test
  public void testFail() {
    Map.Entry<Class<?>, Object> entry = lookup.lookups().entrySet().iterator().next();
    assertFalse( called );
    assertEquals( "asdf", entry.getValue() );
    assertTrue( called );

    try {
      entry.setValue( "asf" );
      fail( "Where is the Exception" );
    } catch ( Exception e ) {
    }
  }
}
