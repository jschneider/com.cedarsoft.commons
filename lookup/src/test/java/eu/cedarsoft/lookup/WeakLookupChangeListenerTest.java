package eu.cedarsoft.lookup;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WeakLookupChangeListenerTest extends TestCase {
  private MockLookup lookup;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    lookup = new MockLookup();
  }

  public void testWeakRemove() {
    LookupChangeListener<String> listener = new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    };
    lookup.addLookupChangeListenerWeak( listener );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    assertSame( listener, ( ( WeakLookupChangeListener<?> ) lookup.getLookupChangeSupport().getListeners().get( 0 ) ).getWrappedListener() );
    lookup.removeChangeListener( listener );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testAdd() {
    LookupChangeListener<String> listener = new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    };
    lookup.addLookupChangeListenerWeak( listener );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    assertSame( listener, ( ( WeakLookupChangeListener<?> ) lookup.getLookupChangeSupport().getListeners().get( 0 ) ).getWrappedListener() );
  }

  public void testWeakDirect() {
    {
      lookup.addLookupChangeListenerWeak( new LookupChangeListener<String>() {
        public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
        }
      } );
    }
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testWeakListener() {
    lookup.addChangeListener( new WeakLookupChangeListener<String>( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } ) );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testWeakFactoryMethod() {
    lookup.addChangeListener( WeakLookupChangeListener.wrap( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } ) );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testWeakBind() {
    lookup.bindWeak( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testWeakBindTyped() {
    lookup.bindWeak( new TypedLookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }

      @NotNull
      public Class<String> getType() {
        return String.class;
      }
    } );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testWeakAdd() {
    lookup.addLookupChangeListenerWeak( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testWeakAdd2() {
    lookup.addLookupChangeListenerWeak( new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  public void testWrap() {
    final List<LookupChangeEvent<?>> events = new ArrayList<LookupChangeEvent<?>>();

    lookup.addChangeListener( WeakLookupChangeListener.wrap( new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
        events.add( event );
      }
    } ) );

    lookup.store( String.class, "asf" );
    assertEquals( 1, events.size() );
  }

  private static void gc() {
    for ( int i = 0; i < 20; i++ ) {
      System.gc();
    }
  }
}
