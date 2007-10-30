package eu.cedarsoft.lookup;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class LookupChangeSupportTest extends TestCase {
  private LookupChangeSupport lookupChangeSupport;
  private MockLookup lookup;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    lookup = new MockLookup();
    lookupChangeSupport = lookup.getLookupChangeSupport();
  }

  public void testListener() {
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
    LookupChangeListenerMock listener = new LookupChangeListenerMock();
    lookup.addChangeListener( listener );
    assertEquals( 1, lookupChangeSupport.getListeners().size() );
    lookup.removeChangeListener( listener );
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
  }

  public void testAddWeak() {
    addWeakListener();
    assertEquals( 2, lookupChangeSupport.getListeners().size() );
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    System.gc();
    assertEquals( 2, lookupChangeSupport.getListeners().size() );
    lookup.store( String.class, "asf" );
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
  }

  private void addWeakListener() {
    lookupChangeSupport.addLookupChangeListenerWeak( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } );
    lookupChangeSupport.addLookupChangeListenerWeak( new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );
  }

  public void testAddListeners() {
    assertEquals( 0, lookupChangeSupport.getListeners().size() );
    lookupChangeSupport.addLookupChangeListener( String.class, new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );
    assertEquals( 1, lookupChangeSupport.getListeners().size() );
    lookupChangeSupport.addLookupChangeListener( new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );

    assertEquals( 2, lookupChangeSupport.getListeners().size() );

    lookupChangeSupport.addLookupChangeListener( String.class, new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );
    assertEquals( 3, lookupChangeSupport.getListeners().size() );
    lookupChangeSupport.addLookupChangeListener( String.class, new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
      }
    } );


    assertEquals( 4, lookupChangeSupport.getListeners().size() );
  }
}
