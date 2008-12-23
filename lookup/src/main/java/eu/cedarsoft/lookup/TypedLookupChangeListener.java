package com.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

/**
 * This is a special LookupChangeListener that knows the type it is listening to.
 * This interface may be used to bind the listener to the {@link Lookup}
 * more easily using the method {@link Lookup#bind(TypedLookupChangeListener)}
 */
public interface TypedLookupChangeListener<T> extends LookupChangeListener<T> {
  /**
   * Returns the type this callback is bound to
   *
   * @return the type
   */
  @NotNull
  Class<T> getType();
}
