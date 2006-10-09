package eu.cedarsoft.lookup;

import junit.framework.TestCase;

import java.util.Map;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:48:38<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class LookupTest extends TestCase {
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testLookup() {
    checkLookupStore( new MappedLookup() );
  }

  void checkLookupStore( LookupStore lookup ) {
    assertNull( lookup.lookup( String.class ) );
    lookup.store( String.class, "asdf" );
    assertEquals( "asdf", lookup.lookup( String.class ) );
  }

  public void testLookups() {
    MappedLookup lookup = new MappedLookup();
    assertTrue( lookup.lookups().isEmpty() );

    lookup.store( String.class, "asdf" );
    assertEquals( 1, lookup.lookups().size() );
    Map.Entry<Class<?>, Object> entry = lookup.lookups().entrySet().iterator().next();
    assertSame( String.class, entry.getKey() );
    assertEquals( "asdf", entry.getValue() );
  }
}
