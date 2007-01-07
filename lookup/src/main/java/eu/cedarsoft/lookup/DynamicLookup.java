package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 * Date: 07.10.2006<br>
 * Time: 17:58:31<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class DynamicLookup implements Lookup {
  private final Map<Class<?>, Object> store = new HashMap<Class<?>, Object>();

  public DynamicLookup( @NotNull Object value ) {

    //Create the store map
    //Super classes
    Class<? extends Object> type = value.getClass();
    while ( type != null ) {
      store.put( type, value );
      type = type.getSuperclass();
    }

    //Interfaces
    for ( Class<?> anInterface : value.getClass().getInterfaces() ) {
      store.put( anInterface, value );
    }
  }

  @Nullable
  public <T> T lookup( @NotNull Class<T> type ) {
    return type.cast( store.get( type ) );
  }

  @NotNull
  public Map<Class<?>, Object> lookups() {
    return Collections.unmodifiableMap( store );
  }

  /*
   * Lookup may not be changed --> do nothing
   */

  public void addLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
  }

  public <T> void addLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
  }

  public void removeLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
  }

  public <T> void removeLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
  }
}
