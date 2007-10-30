package eu.cedarsoft.lookup;

import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class InstantiatorLookup<T> extends LazyLookup<T> {
  @NotNull
  private final Instantiater<T> instantiater;

  private Class<? extends T> type;

  public InstantiatorLookup( @NotNull Class<? extends T> type, @NotNull Instantiater<T> instantiater ) {
    this.type = type;
    this.instantiater = instantiater;
  }

  public InstantiatorLookup( @NotNull Instantiater.Typed<T> instantiater ) {
    this.instantiater = instantiater;
  }

  @Override
  @NotNull
  protected T createInstance() {
    try {
      return instantiater.createInstance();
    } catch ( InstantiationFailedException e ) {
      throw new RuntimeException( e );
    }
  }

  @Override
  public Class<? extends T> getType() {
    if ( type != null ) {
      return type;
    }
    return ( ( Instantiater.Typed<T> ) instantiater ).getType();
  }
}
