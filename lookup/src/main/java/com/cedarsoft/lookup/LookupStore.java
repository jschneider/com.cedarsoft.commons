package com.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

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
}
