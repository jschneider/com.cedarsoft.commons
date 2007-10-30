package eu.cedarsoft.lookup;

import junit.framework.TestCase;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:55:30<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class ListenerTest extends TestCase {
  private LookupStore lookup;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    lookup = new MappedLookup();
  }

  public void testListeners() {
    LookupChangeListenerMock listenerMock = new LookupChangeListenerMock();
    lookup.addChangeListener( listenerMock );

    listenerMock.addExpected( String.class, null, "asdf" );
    lookup.store( String.class, "asdf" );
    listenerMock.verify();

    lookup.removeChangeListener( listenerMock );

    lookup.store( String.class, "aa" );

    lookup.addChangeListener( listenerMock );
    listenerMock.addExpected( String.class, "aa", "bb" );

    lookup.store( String.class, "bb" );
    listenerMock.verify();
  }

  public void testOther() {
    LookupChangeListenerMock listenerMock = new LookupChangeListenerMock();
    lookup.addChangeListener( listenerMock );

    listenerMock.addExpected( String.class, null, "asdf" );
    lookup.store( String.class, "asdf" );
    listenerMock.verify();


    lookup.removeChangeListener( listenerMock );
    lookup.addChangeListener( Object.class, listenerMock );

    lookup.store( String.class, "asdfasdf" );
    listenerMock.verify();

    listenerMock.addExpected( Object.class, null, "asdf2" );
    lookup.store( Object.class, "asdf2" );
    listenerMock.verify();
  }
}
