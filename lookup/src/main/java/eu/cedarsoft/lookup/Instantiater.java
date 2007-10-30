package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

/**
 * Creates an instance.
 * Implementations may be used to instantiate elements lazy.
 */
public interface Instantiater<T> {
  /**
   * Creates the instance
   *
   * @return the instance
   *
   * @throws InstantiationFailedException
   */
  @NotNull
  T createInstance() throws InstantiationFailedException;

  /**
   * A typed instantiator.
   */
  interface Typed<T> extends Instantiater<T> {
    @NotNull
    Class<? extends T> getType();
  }
}