package eu.cedarsoft.lookup;

import junit.framework.TestCase;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

/**
 * <p/>
 * Date: 07.10.2006<br>
 * Time: 17:57:45<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class DynamicLookupTest extends TestCase {

  public void testMultiple() {
    DynamicLookup lookup = Lookups.dynamicLookup( new AbstractAction( "myAction" ) {
      public void actionPerformed( ActionEvent e ) {
        throw new UnsupportedOperationException();
      }
    } );

    assertEquals( 8, lookup.lookups().size() );
    assertNotNull( lookup.lookup( Action.class ) );
    assertNotNull( lookup.lookup( AbstractAction.class ) );
  }

  public void testListeners() {
    DynamicLookup lookup = new DynamicLookup( "asdf" );
    LookupChangeListenerMock mock = new LookupChangeListenerMock();
    lookup.addChangeListener( mock );

    mock.addExpected( String.class, "asdf", "new" );
    mock.addExpected( Serializable.class, "asdf", "new" );
    mock.addExpected( Comparable.class, "asdf", "new" );
    mock.addExpected( CharSequence.class, "asdf", "new" );
    mock.addExpected( Object.class, "asdf", "new" );
    lookup.store( String.class, "new" );

    mock.verify();
  }

  public void testIt() {
    DynamicLookup lookup = new DynamicLookup( "asdf" );
    assertFalse( lookup.lookups().isEmpty() );

    assertEquals( "asdf", lookup.lookup( String.class ) );
    assertEquals( "asdf", lookup.lookup( Object.class ) );
    assertNull( lookup.lookup( Integer.class ) );
    assertNull( lookup.lookup( List.class ) );
    assertEquals( "asdf", lookup.lookup( CharSequence.class ) );

    Map<Class<?>, Object> map = lookup.lookups();
    assertEquals( 5, map.size() );
  }

  public void testInterfaces() {
    DynamicLookup lookup = new DynamicLookup( new ArrayList() );

    assertNotNull( lookup.lookup( ArrayList.class ) );
    assertNotNull( lookup.lookup( AbstractList.class ) );
    assertNotNull( lookup.lookup( List.class ) );
    assertNotNull( lookup.lookup( RandomAccess.class ) );
    assertNotNull( lookup.lookup( Object.class ) );
  }
}

