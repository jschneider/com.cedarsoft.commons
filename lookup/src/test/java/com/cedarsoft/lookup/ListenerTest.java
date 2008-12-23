package com.cedarsoft.lookup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:55:30<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class ListenerTest  {
  private LookupStore lookup;

  @BeforeMethod
  protected void setUp() throws Exception {
    lookup = new MappedLookup();
  }

  @Test
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

  @Test
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
