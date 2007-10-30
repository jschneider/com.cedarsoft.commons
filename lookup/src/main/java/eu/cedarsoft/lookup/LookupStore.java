package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A lookup store is an extended lookup with write access
 */
public interface LookupStore extends Lookup {
  /**
   * A lookup store is able to add elements to the lookup
   *
   * @param type  the type
   * @param value the value
   */
  <T> void store( @NotNull Class<T> type, @NotNull T value );

  void addLookupChangeListenerWeak( @NotNull LookupChangeListener<?> lookupChangeListener );

  <T> void addLookupChangeListenerWeak( @Nullable Class<T> type, @NotNull LookupChangeListener<T> lookupChangeListener );
}
