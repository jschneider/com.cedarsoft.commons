package com.cedarsoft.lookup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:48:38<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class LookupTest  {
  @BeforeMethod
  protected void setUp() throws Exception {
  }

  @Test
  public void testSimpleLookup() {
    Lookup lookup = Lookups.createLookup( "a", 5 );
    assertEquals( 2, lookup.lookups().size() );
    assertTrue( lookup.lookups().keySet().contains( String.class ) );
    assertTrue( lookup.lookups().keySet().contains( Integer.class ) );
  }

  @Test
  public void testLookup() {
    checkLookupStore( new MappedLookup() );
  }

  void checkLookupStore( LookupStore lookup ) {
    assertNull( lookup.lookup( String.class ) );
    lookup.store( String.class, "asdf" );
    assertEquals( "asdf", lookup.lookup( String.class ) );
  }

  @Test
  public void testLookups() {
    MappedLookup lookup = new MappedLookup();
    assertTrue( lookup.lookups().isEmpty() );

    lookup.store( String.class, "asdf" );
    assertEquals( 1, lookup.lookups().size() );
    Map.Entry<Class<?>, Object> entry = lookup.lookups().entrySet().iterator().next();
    assertSame( String.class, entry.getKey() );
    assertEquals( "asdf", entry.getValue() );
  }

  @Test
  public void testLookupsFromList() {
    List<Object> objects = new ArrayList<Object>();
    assertEquals( 0, Lookups.dynamicLookupFromList( objects ).lookups().size() );

    objects.add( "asdf" );
    assertEquals( "asdf", Lookups.dynamicLookupFromList( objects ).lookup( String.class ) );
  }
}
