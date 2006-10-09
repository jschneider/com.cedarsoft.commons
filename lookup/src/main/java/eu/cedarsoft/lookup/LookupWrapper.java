package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * Date: 09.10.2006<br>
 * Time: 09:00:23<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class LookupWrapper implements Lookup, LookupStore {
  private Lookup wrappedLookup;
  private Map<Class<?>, Object> store = new HashMap<Class<?>, Object>();

  private LookupChangeSupport lcs = new LookupChangeSupport( this );

  public LookupWrapper( @NotNull Lookup wrappedLookup ) {
    this.wrappedLookup = wrappedLookup;
    this.wrappedLookup.addLookupChangeListener( new LookupChangeListener<Object>() {
      public void lookupChange( @NotNull LookupChangedEvent<? extends Object> event ) {
        //If it is overridden by the "local" store do nothing
        if ( store.get( event.getType() ) != null ) {
          return;
        }

        lcs.fireLookupChanged( ( Class<Object> ) event.getType(), event.getOldValue(), event.getNewValue() );
      }
    } );
  }

  public void addLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListener( lookupChangeListener );
  }

  public void removeLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.removeLookupChangeListener( lookupChangeListener );
  }

  public <T> void addLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addLookupChangeListener( type, lookupChangeListener );
  }

  public <T> void removeLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
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
    T old = type.cast( store.put( type, value ) );
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
