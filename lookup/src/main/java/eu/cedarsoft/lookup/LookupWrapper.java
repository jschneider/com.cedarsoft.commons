package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper for a lookup
 */
public class LookupWrapper implements LookupStore {
  private Lookup wrappedLookup;
  private Map<Class<?>, Object> store = new HashMap<Class<?>, Object>();

  private LookupChangeSupport lcs = new LookupChangeSupport( this );

  /**
   * Creates a new lookup wrapper
   *
   * @param wrappedLookup the wrapped lookup
   */
  public LookupWrapper( @NotNull Lookup wrappedLookup ) {
    this.wrappedLookup = wrappedLookup;
    this.wrappedLookup.addChangeListener( new LookupChangeListener<Object>() {
      public void lookupChanged( @NotNull LookupChangeEvent<? extends Object> event ) {
        //If it is overridden by the "local" store do nothing
        if ( store.get( event.getType() ) != null ) {
          return;
        }

        lcs.fireLookupChanged( ( Class<Object> ) event.getType(), event.getOldValue(), event.getNewValue() );
      }
    } );
  }

  public <T> void bind( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.bind( type, lookupChangeListener );
  }

  public <T> void bind( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    lcs.bind( lookupChangeListener );
  }

  public <T> void bindWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.bindWeak( type, lookupChangeListener );
  }

  public <T> void bindWeak( @NotNull TypedLookupChangeListener<T> lookupChangeListener ) {
    lcs.bindWeak( lookupChangeListener );
  }

  public void addChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( lookupChangeListener );
  }

  public <T> void addChangeListenerWeak( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( type, lookupChangeListener );
  }

  public <T> void addLookupChangeListenerWeak( @Nullable Class<T> type, @NotNull LookupChangeListener<T> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( type, lookupChangeListener );
  }

  public void addLookupChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListenerWeak( lookupChangeListener );
  }

  public void addChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListener( lookupChangeListener );
  }

  public void removeChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.removeLookupChangeListener( lookupChangeListener );
  }

  public <T> void addChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addLookupChangeListener( type, lookupChangeListener );
  }

  public <T> void removeChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.removeLookupChangeListener( type, lookupChangeListener );
  }

  @Nullable
  public <T> T lookup( @NotNull Class<T> type ) {
    Object value = store.get( type );
    if ( value != null ) {
      return type.cast( value );
    }
    return wrappedLookup.lookup( type );
  }

  public <T> void store( @NotNull Class<T> type, @NotNull T value ) {
    T old = type.cast( lookup( type ) );
    store.put( type, value );
    lcs.fireLookupChanged( type, old, value );
  }

  @NotNull
  public Map<Class<?>, Object> lookups() {
    Map<Class<?>, Object> lookups = new HashMap<Class<?>, Object>();

    lookups.putAll( wrappedLookup.lookups() );
    lookups.putAll( store );

    return Collections.unmodifiableMap( lookups );
  }

}
