package eu.cedarsoft.lookup;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class InstantiatorLookupTest extends TestCase {
  private InstantiatorLookup<String> lookup;
  private boolean called;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    Instantiater<String> instantiater = new Instantiater<String>() {
      @NotNull
      public String createInstance() throws InstantiationFailedException {
        if ( called ) {
          throw new IllegalStateException();
        }
        called = true;
        return "asdf";
      }
    };
    lookup = new InstantiatorLookup<String>( String.class, instantiater );
  }

  public void testIt() {
    assertFalse( called );
    assertEquals( 4, lookup.lookups().size() );
    assertFalse( called );
    assertNull( lookup.lookup( Integer.class ) );
    assertFalse( called );
    assertEquals( "asdf", lookup.lookup( String.class ) );
    assertTrue( called );
    assertEquals( "asdf", lookup.lookup( String.class ) );
    assertTrue( called );
  }
}
