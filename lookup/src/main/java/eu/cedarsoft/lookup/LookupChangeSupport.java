package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 17:04:00<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class LookupChangeSupport {
  private static final LookupChangeListener<?>[] EMPTY_LISTENERS_ARRAY = new LookupChangeListener[0];
  private static final Object[] EMPTY_ARRAY = new Object[0];

  private Object[] listeners = EMPTY_ARRAY;
  private Lookup source;

  public LookupChangeSupport( @NotNull Lookup source ) {
    this.source = source;
  }

  @NotNull
  public Object getSource() {
    return source;
  }

  public void addLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    addLookupChangeListener( null, lookupChangeListener );
  }

  public <T> void addLookupChangeListener( @Nullable Class<?> type, @NotNull LookupChangeListener<?> lookupChangeListener ) {
    Object[] newListeners = new Object[listeners.length + 2];
    System.arraycopy( listeners, 0, newListeners, 0, listeners.length );
    newListeners[ listeners.length ] = type;
    newListeners[ listeners.length + 1 ] = lookupChangeListener;
    this.listeners = newListeners;
  }

  public void removeLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    removeLookupChangeListener( null, lookupChangeListener );
  }

  public <T> void removeLookupChangeListener( @Nullable Class<?> type, @NotNull LookupChangeListener<?> lookupChangeListener ) {
    for ( int i = 0; i < listeners.length; i += 2 ) {
      if ( listeners[ i ] == type && listeners[ i + 1 ] == lookupChangeListener ) {
        Object[] newListeners = new Object[listeners.length - 2];
        System.arraycopy( listeners, 0, newListeners, 0, i );
        System.arraycopy( listeners, i + 2, newListeners, i, listeners.length - ( i + 2 ) );
        this.listeners = newListeners;
        return;
      }
    }
  }

  public <T> void fireLookupChanged( @NotNull Class<? super T> type, @Nullable T oldValue, @Nullable T value ) {
    fireLookupChanged( new LookupChangedEvent<T>( source, type, oldValue, value ) );
  }

  private <T> void fireLookupChanged( @NotNull LookupChangedEvent<T> event ) {
    {
      LookupChangeListener<? super T>[] listenerArray = findListeners( event.getType() );
      if ( listenerArray.length > 0 ) {
        for ( LookupChangeListener<? super T> lookupChangeListener : listenerArray ) {
          lookupChangeListener.lookupChange( event );
        }
      }
    }

    {
      LookupChangeListener<? super T>[] listenerArray = findListeners( null );
      if ( listenerArray.length > 0 ) {
        for ( LookupChangeListener<? super T> lookupChangeListener : listenerArray ) {
          lookupChangeListener.lookupChange( event );
        }
      }
    }
  }

  private <T> LookupChangeListener<T>[] findListeners( @Nullable Class<T> aClass ) {
    //first check if at least one listener is registered
    int size = 0;
    for ( int i = 0; i < listeners.length; i += 2 ) {
      if ( listeners[ i ] == aClass ) {
        size++;
      }
    }

    if ( size == 0 ) { //no listener has been found
      return ( LookupChangeListener<T>[] ) EMPTY_LISTENERS_ARRAY;
    }

    LookupChangeListener<T>[] result = ( LookupChangeListener<T>[] ) new LookupChangeListener[size];
    int index = 0;
    for ( int i = 0; i < listeners.length; i += 2 ) {
      if ( listeners[ i ] == aClass ) {
        result[ index++ ] = ( LookupChangeListener<T> ) listeners[ i + 1 ];
      }
    }
    return result;
  }

  public void fireLookupChanged() {
  }
}
