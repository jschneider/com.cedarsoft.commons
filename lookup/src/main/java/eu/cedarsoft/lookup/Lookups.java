package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

/**
 * This is a utily class that creates several lookups for special purposes
 * <p/>
 * Date: 07.10.2006<br>
 * Time: 13:03:35<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public class Lookups {

  /**
   * Creates a singleton lookup
   *
   * @param type  the type
   * @param value the value
   * @return the singleton lookup
   */
  public <T> Lookup singletonLookup( @NotNull Class<T> type, @NotNull T value ) {
    return new SingletonLookup<T>( type, value );
  }

  /**
   * Create a dynamic lookup
   *
   * @param value the value
   * @return the dynamik lookup
   */
  public Lookup dynamicLookup( @NotNull Object value ) {
    return new DynamicLookup( value );
  }

  /**
   * Creates an empty lookup
   *
   * @return the empty lookup
   */
  public Lookup emtyLookup() {
    return new Lookup() {
      @Nullable
      public <T> T lookup( @NotNull Class<T> type ) {
        return null;
      }

      @NotNull
      public Map<Class<?>, Object> lookups() {
        return Collections.emptyMap();
      }

      public void addLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
      }

      public <T> void addLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
      }

      public void removeLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener ) {
      }

      public <T> void removeLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener ) {
      }
    };
  }

}
