package eu.cedarsoft.lookup;

import junit.framework.TestCase;

/**
 * <p/>
 * Date: 09.10.2006<br>
 * Time: 09:00:49<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class LookupWrapperTest extends TestCase {
  private MappedLookup lookup;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    lookup = new MappedLookup();
    lookup.store( String.class, "asdf" );
  }

  public void testWrapping() {
    LookupChangeListenerMock listenerMock = new LookupChangeListenerMock();

    LookupWrapper lookupWrapper = new LookupWrapper( lookup );
    assertEquals( "asdf", lookupWrapper.lookup( String.class ) );
    lookupWrapper.addChangeListener( listenerMock );

    listenerMock.addExpected( String.class, "asdf", "2" );
    lookup.store( String.class, "2" );
    listenerMock.verify();
    assertEquals( "2", lookup.lookup( String.class ) );
    assertEquals( "2", lookupWrapper.lookup( String.class ) );

    listenerMock.addExpected( String.class, "2", "3" );

    lookupWrapper.store( String.class, "3" );
    listenerMock.verify();

    assertEquals( "2", lookup.lookup( String.class ) );
    assertEquals( "3", lookupWrapper.lookup( String.class ) );
  }

}
