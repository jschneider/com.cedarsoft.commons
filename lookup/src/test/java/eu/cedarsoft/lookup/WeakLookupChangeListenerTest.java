package eu.cedarsoft.lookup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class WeakLookupChangeListenerTest  {
  private MockLookup lookup;

  @BeforeMethod
  protected void setUp() throws Exception {
    lookup = new MockLookup();
  }

  @Test
  public void testWeakRemove() {
    LookupChangeListener<String> listener = new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    };
    lookup.addChangeListenerWeak( listener );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    assertSame( listener, ( ( WeakLookupChangeListener<?> ) lookup.getLookupChangeSupport().getListeners().get( 0 ) ).getWrappedListener() );
    lookup.removeChangeListener( listener );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  @Test
  public void testAdd() {
    LookupChangeListener<String> listener = new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    };
    lookup.addChangeListenerWeak( listener );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    assertSame( listener, ( ( WeakLookupChangeListener<?> ) lookup.getLookupChangeSupport().getListeners().get( 0 ) ).getWrappedListener() );
  }

  @Test
  public void testWeakDirect() {
    {
      lookup.addChangeListenerWeak( new LookupChangeListener<String>() {
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

  @Test
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

  @Test
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

  @Test
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

  @Test
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

  @Test
  public void testWeakAdd() {
    lookup.addChangeListenerWeak( String.class, new LookupChangeListener<String>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends String> event ) {
      }
    } );
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );
    gc();
    assertEquals( 1, lookup.getLookupChangeSupport().getListeners().size() );

    lookup.store( String.class, "asf" );
    assertEquals( 0, lookup.getLookupChangeSupport().getListeners().size() );
  }

  @Test
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

  @Test
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
