package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

/**
 * This is a simpel Lookup that contains just one object under a given key
 * <p/>
 * Date: 07.10.2006<br>
 * Time: 13:06:07<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class SingletonLookup<T> implements Lookup {
  private final T singleton;
  private final Class<T> singletonType;

  public SingletonLookup( @NotNull Class<T> type, @NotNull T value ) {
    this.singletonType = type;
    this.singleton = value;
  }

  @Nullable
  public <T> T lookup( @NotNull Class<T> type ) {
    if ( type == this.singletonType ) {
      return type.cast( singleton );
    }
    return null;
  }

  @NotNull
  public Map<Class<?>, Object> lookups() {
    return Collections.<Class<?>, Object>singletonMap( ( Class<?> ) singletonType, ( Object ) singleton );
  }

  @NotNull
  public T getSingleton() {
    return singleton;
  }

  @NotNull
  public Class<T> getSingletonType() {
    return singletonType;
  }

  /*
   * The value can't be changed. Therefore the listeners are not supported
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
