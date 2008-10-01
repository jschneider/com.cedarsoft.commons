package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LookupChangeSupport
 */
public class LookupChangeSupport {
  private static final LookupChangeListener<?>[] EMPTY_LISTENERS_ARRAY = new LookupChangeListener[0];
  private static final Object[] EMPTY_ARRAY = new Object[0];

  private Object[] listeners = EMPTY_ARRAY;
  @NotNull
  private final Lookup source;

  /**
   * Creates a new lookup change support
   *
   * @param source the source
   */
  public LookupChangeSupport( @NotNull Lookup source ) {
    this.source = source;
  }

  @NotNull
  public Object getSource() {
    return source;
  }

  /**
   * Binds the given lookup change listener. Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  public <T> void bind( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    addChangeListener( type, lookupChangeListener );
    lookupChangeListener.lookupChanged( new LookupChangeEvent<T>( source, type, null, source.lookup( type ) ) );
  }

  /**
   * Binds the given lookup change listener with the key retrieved from {@link TypedLookupChangeListener#getType()}.
   * Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  public <T> void bind( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    Class<T> type = lookupChangeListener.getType();
    bind( type, lookupChangeListener );
  }

  /**
   * Binds the given lookup change listener that is wrapped within a {@link eu.cedarsoft.lookup.WeakLookupChangeListener}.
   * Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  public <T> void bindWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    addChangeListenerWeak( type, lookupChangeListener );
    lookupChangeListener.lookupChanged( new LookupChangeEvent<T>( source, type, null, source.lookup( type ) ) );
  }

  /**
   * Binds the given lookup change listener (that is wrapped within a {@link eu.cedarsoft.lookup.WeakLookupChangeListener})
   * with the key retrieved from {@link TypedLookupChangeListener#getType()}.
   * Adds the given listener and calls
   * {@link LookupChangeListener#lookupChanged(LookupChangeEvent)} for the first time.
   *
   * @param lookupChangeListener the listener that is added
   */
  public <T> void bindWeak( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    Class<T> type = lookupChangeListener.getType();
    bindWeak( type, lookupChangeListener );
  }

  /**
   * Adds a lookup change listener that is wrapped within a {@link WeakLookupChangeListener}
   *
   * @param lookupChangeListener the listener that is wrapped and added
   */
  public void addChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    addChangeListener( WeakLookupChangeListener.wrap( lookupChangeListener ) );
  }

  /**
   * Adds a lookup change listener that is wrapped within a {@link WeakLookupChangeListener}
   *
   * @param type                 the type the listener is registered for
   * @param lookupChangeListener the listener that is wrapped and added
   */
  public <T> void addChangeListenerWeak( @Nullable Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    addChangeListener( type, WeakLookupChangeListener.wrap( type, lookupChangeListener ) );
  }

  public void addChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    addChangeListener( null, lookupChangeListener );
  }

  public void addChangeListener( @Nullable Class<?> type, @NotNull LookupChangeListener<?> lookupChangeListener ) {
    Object[] newListeners = new Object[listeners.length + 2];
    System.arraycopy( listeners, 0, newListeners, 0, listeners.length );
    newListeners[listeners.length] = type;
    newListeners[listeners.length + 1] = lookupChangeListener;
    this.listeners = newListeners;
  }

  public void removeChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    removeChangeListener( null, lookupChangeListener );
  }

  public void removeChangeListener( @Nullable Class<?> type, @NotNull LookupChangeListener<?> lookupChangeListener ) {
    for ( int i = 0; i < listeners.length; i += 2 ) {
      if ( listeners[i] == type ) {
        Object listener = listeners[i + 1];
        if ( listener == lookupChangeListener || listener instanceof WeakLookupChangeListener && ( ( WeakLookupChangeListener<?> ) listener ).getWrappedListener() == lookupChangeListener ) {
          Object[] newListeners = new Object[listeners.length - 2];
          System.arraycopy( listeners, 0, newListeners, 0, i );
          System.arraycopy( listeners, i + 2, newListeners, i, listeners.length - ( i + 2 ) );
          this.listeners = newListeners;
          return;
        }
      }
    }
  }

  public <T> void fireLookupChanged( @NotNull Class<? super T> type, @Nullable T oldValue, @Nullable T value ) {
    //noinspection ObjectEquality
    if ( oldValue == value ) {
      return;
    }
    fireLookupChanged( new LookupChangeEvent<T>( source, type, oldValue, value ) );
  }

  public <T> void fireLookupChanged( @NotNull LookupChangeEvent<T> event ) {
    if ( event.getNewValue() == event.getOldValue() ) {
      return;
    }

    {
      LookupChangeListener<? super T>[] listenerArray = findListeners( event.getType() );
      if ( listenerArray.length > 0 ) {
        for ( LookupChangeListener<? super T> lookupChangeListener : listenerArray ) {
          lookupChangeListener.lookupChanged( event );
        }
      }
    }

    {
      LookupChangeListener<? super T>[] listenerArray = findListeners( null );
      if ( listenerArray.length > 0 ) {
        for ( LookupChangeListener<? super T> lookupChangeListener : listenerArray ) {
          lookupChangeListener.lookupChanged( event );
        }
      }
    }
  }

  @NotNull
  public List<LookupChangeListener<?>> getListeners() {
    List<LookupChangeListener<?>> changeListeners = new ArrayList<LookupChangeListener<?>>();

    for ( int i = 0; i < listeners.length; i += 2 ) {
      Object listener = listeners[i + 1];
      changeListeners.add( ( LookupChangeListener<?> ) listener );
    }

    return changeListeners;
  }


  private <T> LookupChangeListener<T>[] findListeners( @Nullable Class<T> aClass ) {
    //first check if at least one listener is registered
    int size = 0;
    for ( int i = 0; i < listeners.length; i += 2 ) {
      if ( listeners[i] == aClass ) {
        size++;
      }
    }

    if ( size == 0 ) {//no listener has been found
      return ( LookupChangeListener<T>[] ) EMPTY_LISTENERS_ARRAY;
    }

    LookupChangeListener<T>[] result = ( LookupChangeListener<T>[] ) new LookupChangeListener[size];
    int index = 0;
    for ( int i = 0; i < listeners.length; i += 2 ) {
      if ( listeners[i] == aClass ) {
        result[index++] = ( LookupChangeListener<T> ) listeners[i + 1];
      }
    }
    return result;
  }


  public void fireDelta( @Nullable Lookup oldLookup, @Nullable Lookup newLookup ) {
    if ( listeners.length == 0 ) {
      return;
    }

    Map<Class<?>, Object> oldLookups = new HashMap<Class<?>, Object>();
    if ( oldLookup != null ) {
      oldLookups.putAll( oldLookup.lookups() );
    }

    fireDelta( oldLookups, newLookup );
  }

  public void fireDelta( Map<Class<?>, Object> oldEntries, Lookup newLookup ) {
    if ( listeners.length == 0 ) {
      return;
    }

    Map<Class<?>, Object> newLookups = new HashMap<Class<?>, Object>();
    if ( newLookup != null ) {
      newLookups.putAll( newLookup.lookups() );//continue
    }

    Map<Class<?>, Object> onlyOld = new HashMap<Class<?>, Object>( oldEntries );
    onlyOld.keySet().removeAll( newLookups.keySet() );
    //Notify old ones
    for ( Map.Entry<Class<?>, Object> entry : onlyOld.entrySet() ) {
      fireLookupChanged( ( Class<Object> ) entry.getKey(), entry.getValue(), null );
    }
    //Notify new ones
    for ( Map.Entry<Class<?>, Object> entry : newLookups.entrySet() ) {
      Object oldValue = oldEntries.get( entry.getKey() );
      Object newValue = entry.getValue();
      //noinspection ObjectEquality
      if ( oldValue == newValue ) {
        continue;
      }
      fireLookupChanged( ( Class<Object> ) entry.getKey(), oldValue, newValue );
    }
  }
}
