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
  public void testRemove() {
    DynamicLookup lookup = new DynamicLookup();
    assertTrue( lookup.addValue( "asdf" ) );
    assertFalse( lookup.addValue( "asdf" ) );

    assertTrue( lookup.removeValue( "asdf" ) );
    assertFalse( lookup.removeValue( "asdf" ) );
  }

  public void testRemove2() {
    DynamicLookup lookup = new DynamicLookup();

    assertTrue( lookup.addValue( 5 ) );
    assertEquals( 5, lookup.lookups().size() );
    assertTrue( lookup.addValue( 6 ) );

    assertEquals( 5, lookup.lookups().size() );

    assertTrue( lookup.removeValue( 1 ) );

    assertEquals( 0, lookup.lookups().size() );
  }

  public void testRmove3() {
    DynamicLookup lookup = new DynamicLookup();

    assertTrue( lookup.addValue( 5 ) );
    assertEquals( 5, lookup.lookups().size() );
    assertTrue( lookup.removeValue( 1L ) );
    assertEquals( 1, lookup.lookups().size() );
    assertNotNull( lookup.lookup( Integer.class ) );
  }

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
    Object value = new ArrayList();
    DynamicLookup lookup = new DynamicLookup( value );

    assertNotNull( lookup.lookup( ArrayList.class ) );
    assertNotNull( lookup.lookup( AbstractList.class ) );
    assertNotNull( lookup.lookup( List.class ) );
    assertNotNull( lookup.lookup( RandomAccess.class ) );
    assertNotNull( lookup.lookup( Object.class ) );

    lookup.removeValue( value );

    assertNull( lookup.lookup( ArrayList.class ) );
    assertNull( lookup.lookup( AbstractList.class ) );
    assertNull( lookup.lookup( List.class ) );
    assertNull( lookup.lookup( RandomAccess.class ) );
    assertNull( lookup.lookup( Object.class ) );

    assertEquals( 0, lookup.lookups().size() );
  }
}

