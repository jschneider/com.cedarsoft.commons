package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * A lookup offers a simple way to get "extensions" of the current object.
 * <p/>
 * Date: 06.10.2006<br>
 * Time: 16:46:47<br>
 *
 * @author <a href="http://johannes-schneider.info">Johannes Schneider</a> -
 *         <a href="http://www.xore.de">Xore Systems</a>
 */
public interface Lookup {
  /**
   * Lookup a given object
   *
   * @param type the type
   * @return the lookup object or null if nothing has been found
   */
  @Nullable
      <T> T lookup( @NotNull Class<T> type );

  /**
   * Retuns a  map containing the available lookup objects.
   * This method can throw an {@link UnsupportedOperationException} if the map is not available.
   * If no exception is thrown the map must contain every possible lookup object.
   *
   * @return a  map containing the available lookup objects.
   * @throws UnsupportedOperationException if this method is not supported
   */
  @NotNull
  Map<Class<?>, Object> lookups();


  /**
   * Adds a lookup change listener
   *
   * @param lookupChangeListener the lookup change listener
   */
  void addLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener );

  /**
   * Add a lookup change listener for a given type
   *
   * @param type                 the type
   * @param lookupChangeListener the listener
   */
  <T> void addLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener );

  /**
   * Remove a lookup change listener
   *
   * @param lookupChangeListener the listener that is removed from *all* types
   */
  void removeLookupChangeListener( @NotNull LookupChangeListener<?> lookupChangeListener );

  /**
   * Remove a lookup change listener from the given type
   *
   * @param type                 the type of the listener
   * @param lookupChangeListener the listener
   */
  <T> void removeLookupChangeListener( @NotNull Class<T> type, @NotNull LookupChangeListener<? super T> lookupChangeListener );
}
