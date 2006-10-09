package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a simple lookup implementation that is backed up by a HashMap
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:49:12<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class MappedLookup implements Lookup, LookupStore {
  private Map<Class<?>, Object> store = new HashMap<Class<?>, Object>();

  private LookupChangeSupport lcs = new LookupChangeSupport( this );

  public <T> void store( @NotNull Class<T> type, @NotNull T value ) {
    T oldValue = type.cast( store.put( type, value ) );
    lcs.fireLookupChanged( type, oldValue, value );
  }

  @NotNull
  public Map<Class<?>, Object> lookups() {
    return Collections.unmodifiableMap( store );
  }

  @Nullable
  public <T> T lookup( @NotNull Class<T> type ) {
    return type.cast( store.get( type ) );
  }

  public void addLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.addLookupChangeListener( lookupChangeListener );
  }

  public <T> void addLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.addLookupChangeListener( type, lookupChangeListener );
  }

  public void removeLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
    lcs.removeLookupChangeListener( lookupChangeListener );
  }

  public <T> void removeLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
    lcs.removeLookupChangeListener( type, lookupChangeListener );
  }
}

